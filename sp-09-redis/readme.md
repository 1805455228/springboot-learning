## 基于redis 实现的分布式锁 

* 单体应用JVM层面的锁
    - Synchronized
    - Lock
        
* 利用缓存的原子性来设计锁，redis 分布式锁 使用 redisson框架
    - SETNX 原子set （存在key不能set）
    - 异常 == try finally{unlock}
    - 突然kill == 超时机制设置
    - 高并发锁失效 == finally下 uuid 线程标识释放锁

* 造成死锁问题 以及解决方案（导致锁释放不了）

    - 程序异常没来得及释放锁 造成死锁 - try finally{unlock}
    
    - 突然kill调线程就成死锁  - 加超时机制后释放锁
    
    - 高并发被别的线程释放锁 （锁失效）（key 是 uuid （线程标识释放锁））


* 缓存穿透、缓存雪崩、缓存击穿 

    - 缓存穿透 
        - 程序在处理缓存时，一般是先从缓存查询，如果缓存没有这个key获取为null，则会从DB中查询，并设置到缓存中去。
          
          按这种做法，那查询一个一定不存在的数据值，由于缓存是不命中时需要从数据库查询，查不到数据则不写入缓存，这将导致这个不存在的数据每次请求都要到数据库去查询，造成缓存穿透。
      
        - 解决方法   
            
            - 最好对于每一个缓存key都有一定的规范约束，这样在程序中对不符合parttern的key 的请求可以拒绝。（但一般key都是通过程序自动生成的）
           
            - 布隆过滤器(保存的订单的指纹信息，hash算法) ==guava框架保护缓存（缓存防火墙、要牺牲空间== 将可能出现的缓存key的组合方式的所有数值以hash形式存储在一个很大的bitmap中<布隆过滤器>（需要考虑如何将这个可能出现的数据的hash值之后同步到bitmap中， eg. 后端每次新增一个可能的组合就同步一次，或者 穷举），一个一定不存在的数据会被这个bitmap拦截掉，从而避免了对底层存储系统的查询压力
            
            - 常用：返回空对象 （简单） 如果对应在数据库中的数据都不存在，我们将此key对应的value设置为一个默认的值，比如“NULL”，并设置一个缓存的失效时间。当然这个key的时效比正常的时效要小的多
              
    - 缓存雪崩
        - 指的是大量缓存集中在一段时间内失效，发生大量的缓存穿透，所有的查询都落在数据库上，造成了缓存雪崩。
    
        - 解决方法
        
           -  这个没有完美解决办法，但可以分析用户行为，尽量让失效时间点均匀分布，设置不同的过期时间。
           -  用加分布式锁或者分布式队列的方式保证缓存的单线程（进程）写 （eg. redis的 SETNX），从而避免失效时大量的并发请求落到底层存储系统上。在加锁方法内先从缓存中再获取一次(防止另外的线程优先获取锁已经写入了缓存)，没有再查DB写入缓存。 （当然也可以： 在没有获取锁(tryLock)的线程中一直轮询缓存，至超限时）
            
    - 缓存击穿 
        - 指的是热点key在某个特殊的场景时间内恰好失效了，恰好有大量并发请求过来了，造成DB压力。
        
        - 解决方法
            - 与缓存雪崩的解决方法类似： 用加锁或者队列的方式保证缓存的单线程（进程）写，在加锁方法内先从缓存中再获取一次，没有再查DB写入缓存。 
            
            -  还有一种比较好用的（针对缓存雪崩与缓存击穿）：
            
            物理上的缓存是不设置超时时间的（或者超时时间比较长）， 但是在缓存的对象上增加一个属性来标识超时时间（此时间相对小）。 当获取到数据后，校验数据内部的标记时间，判定是否快超时了，如果是，异步发起一个线程（控制好并发）去主动更新该缓存。
            
            这种方式会导致一定时间内，有些请求获取缓存会拿到过期的值，看业务是否能接受而定。
          