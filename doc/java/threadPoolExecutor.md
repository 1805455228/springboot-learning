
## 线程池


### JDK自带的线程池工具

* 单线程线程池newSingle  ---- 慢

    - 没有核心线程：核心线程数为1  （核心员工）

    - 最大线程数： 1 （非核心员工、或者临时工 = max - 核心员工）

    - 无边界队列---2的31次方

    - keepAliveTime：0  线程池中非核心线程空闲的存活时间大小

    -- 任务多时：内存溢出 oom


* 定长线程池newFixThreadPool(10)  ---- 中

    - 没有核心线程：核心线程数为10  （核心员工）

    - 最大线程数： 10 （非核心员工、或者临时工 = max - 核心员工）

    - 无边界队列---2的31次方

    - keepAliveTime：0  线程池中非核心线程空闲的存活时间大小

    -- 任务多时：内存溢出 oom


* 缓存线程池newCachedThreadPool   ---- 快   （外包公司）

    - 没有核心线程：核心线程数为0  （核心员工）

    - 最大线程数： max int的最大值 （非核心员工、或者临时工 = max - 核心员工）

    - 同步队列---意味着只有一个线程成功

    - keepAliveTime：60  线程池中非核心线程空闲的存活时间大小

    - 线程复用机制

    -- 任务多时：导致CPU占用高


### 线程池可以通过ThreadPoolExecutor来创建，我们来看一下它的构造函数：

```
public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize,long keepAliveTime,TimeUnit unit,
   BlockingQueue<Runnable> workQueue,
   ThreadFactory threadFactory,
   RejectedExecutionHandler handler)

```

### 几个核心参数的作用：

* corePoolSize： 线程池核心线程数最大值
* maximumPoolSize： 线程池最大线程数大小
* keepAliveTime： 线程池中非核心线程空闲的存活时间大小
* unit： 线程空闲存活时间单位
* workQueue： 存放任务的阻塞队列
* threadFactory： 用于设置创建线程的工厂，可以给创建的线程设置有意义的名字，可方便排查问题。

* handler： 线城池的饱和策略事件，主要有四种类型。

    - AbortPolicy(抛出一个异常，默认的)
    - DiscardPolicy(直接丢弃任务)
    - DiscardOldestPolicy（丢弃队列里最老的任务，将当前这个任务继续提交给线程池）
    - CallerRunsPolicy（交给线程池调用所在的线程进行处理)



* 线程池的线程复用


### JDK自带的线程池工具不推荐使用


* 根据实际场景进行一个大概的预估，使用自定义参数线程池


```
ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 20, 0L,
        TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10),
        Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());


```


* 提交优先级



核心线程数满了-----放队列------队列满了开非核心线程数-----超过最大非核心线程数-----进行拒绝策略


* 执行优先级

-------队列的任务最后执行




