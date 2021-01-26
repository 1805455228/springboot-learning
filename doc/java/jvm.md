

### jvm结构

*

### jvm调优

* 新生代放不下了，new的对象会直接移到老年代

* 刚new的对象会装进--堆区的新生代区（伊甸园区）

* minorGC回收的是：不再被引用的对象；对象还在被引用就进到老年代区

* 堆区中老年代区 装满了，jvm会进行fullgc

* fullgc会回收整个堆的空间 （发现对象还在被引用，就回收不了；新对象装不进堆中就会oom（内存溢出））




### Jvm调优就是：尽可能减少GC，尤其是fullgc;

* fullgc 会暂停在运行的线程 （当进行gc时；网站会出现卡顿现象）

* fullgc的出现是为了解决：有些对象一会是垃圾一会不是的现象

* 频繁fullgc必须避免，正常一周一次或者几周一次




### jvm参数调优设置

堆

为什么会产生fullgc，因为堆的老年代放满了

每秒6m对象 放堆中

看最高并发量，产生的

老年代:2G  新生代:200m  s0:   s1:

* 参数优化后，几乎不产生GC
老年代:1G  新生代:1.6G  s0:200m   s1:200m



### 单机系统---超高并发---消息中间件

* rocketmq  --- 每秒10万+

* kafaka  --- 每秒10万+

* 单机抗10万+并发    20-30G内存




### 几十种垃圾回收器


Java中Stop-The-World机制简称STW，是在执行垃圾收集算法时，
Java应用程序的其他所有线程都被挂起（除了垃圾收集帮助器之外）。
Java中一种全局暂停现象，全局停顿，所有Java代码停止，
native代码可以执行，但不能与JVM交互；这些现象多半是由于gc引起。


并发标记清除（CMS）垃圾收集器



### jvm调优神器——arthas

Linux arthas

curl -O https://arthas.gitee.io/arthas-boot.jar

二、启动

java -jar arthas-boot.jar

* 列出本机所有进程

```
[root@localhost soft]# java -jar arthas-boot.jar
[INFO] arthas-boot version: 3.4.5
[INFO] Found existing java process, please choose one and input the serial number of the process, eg : 1. Then hit ENTER.
* [1]: 19140 org.apache.catalina.startup.Bootstrap
  [2]: 11620 youjiao-0.0.1-SNAPSHOT.jar
  [3]: 12356 org.apache.catalina.startup.Bootstrap
  [4]: 18982 org.apache.catalina.startup.Bootstrap
  [5]: 23512 org.apache.catalina.startup.Bootstrap
  [6]: 29432 com.yingxin.cloud.fileupload-0.0.1-SNAPSHOT.jar
  [7]: 19066 org.apache.catalina.startup.Bootstrap
  [8]: 12476 org.apache.catalina.startup.Bootstrap
  [9]: 2668 com.yingxin.cloud.arvr-0.0.1-SNAPSHOT.jar
  [10]: 15694 xxlisence-1.0-SNAPSHOT.jar

```

* 选择数字---按enter----进入相应的进程

```
9
[INFO] Start download arthas from remote server: https://arthas.aliyun.com/download/3.4.6?mirror=aliyun
[INFO] File size: 11.99 MB, downloaded size: 769.26 KB, downloading ...
[INFO] File size: 11.99 MB, downloaded size: 1.40 MB, downloading ...
[INFO] File size: 11.99 MB, downloaded size: 2.11 MB, downloading ...
[INFO] File size: 11.99 MB, downloaded size: 2.82 MB, downloading ...
[INFO] File size: 11.99 MB, downloaded size: 3.52 MB, downloading ...
[INFO] File size: 11.99 MB, downloaded size: 4.22 MB, downloading ...
[INFO] File size: 11.99 MB, downloaded size: 4.92 MB, downloading ...
[INFO] File size: 11.99 MB, downloaded size: 5.63 MB, downloading ...
[INFO] File size: 11.99 MB, downloaded size: 6.35 MB, downloading ...
[INFO] File size: 11.99 MB, downloaded size: 7.07 MB, downloading ...
[INFO] File size: 11.99 MB, downloaded size: 7.78 MB, downloading ...
[INFO] File size: 11.99 MB, downloaded size: 8.50 MB, downloading ...
[INFO] File size: 11.99 MB, downloaded size: 9.20 MB, downloading ...
[INFO] File size: 11.99 MB, downloaded size: 9.89 MB, downloading ...
[INFO] File size: 11.99 MB, downloaded size: 10.62 MB, downloading ...
[INFO] File size: 11.99 MB, downloaded size: 11.32 MB, downloading ...
[INFO] Download arthas success.
[INFO] arthas home: /root/.arthas/lib/3.4.6/arthas
[INFO] Try to attach process 2668
[INFO] Attach process 2668 success.
[INFO] arthas-client connect 127.0.0.1 3658
  ,---.  ,------. ,--------.,--.  ,--.  ,---.   ,---.
 /  O  \ |  .--. ''--.  .--'|  '--'  | /  O  \ '   .-'
|  .-.  ||  '--'.'   |  |   |  .--.  ||  .-.  |`.  `-.
|  | |  ||  |\  \    |  |   |  |  |  ||  | |  |.-'    |
`--' `--'`--' '--'   `--'   `--'  `--'`--' `--'`-----'


wiki      https://arthas.aliyun.com/doc
tutorials https://arthas.aliyun.com/doc/arthas-tutorials.html
version   3.4.6
pid       2668
time      2021-01-26 16:18:51
```


* 查看面板信息命令：dashboard



* 查看线程使用情况：thread

    - 查找死锁代码：thread -b



* 查看源码（反编译指定已加载类的源码）：jad

    - jad com.yingxin.arvr.admin.LoginController


* ognl 查看代码的变量



