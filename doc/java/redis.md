
## redis 分布式锁

分布式锁的实现有很多，比如基于数据库、memcached、Redis、系统文件、zookeeper等。它们的核心的理念跟上面的过程大致相同。


### 加锁

加锁实际上就是在redis中，给Key键设置一个值，为避免死锁，并给定一个过期时间。


### try finally{unlock} 释放锁















