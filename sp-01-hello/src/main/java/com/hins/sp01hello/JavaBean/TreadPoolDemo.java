package com.hins.sp01hello.JavaBean;

import java.util.concurrent.*;

/**
 * @author qixuan.chen
 * @date 2019-07-30 22:10
 */
public class TreadPoolDemo {

    public static void main(String[] args) {
        pool01();

        pool02();

        pool03();

        pool04();

        poolMain();


    }

    private static void poolMain() {
        //创建一个线程池对象
        /**
         * 参数信息：
         * int corePoolSize     核心线程大小
         * int maximumPoolSize  线程池最大容量大小
         * long keepAliveTime   线程空闲时，线程存活的时间 （超过核心线程数，才起作用）
         * TimeUnit unit        时间单位
         * BlockingQueue<Runnable> workQueue  任务队列。一个阻塞队列 ，超过最大线程数，任务被放进队列
         */
        ThreadPoolExecutor pool = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(10));
        //另外一种写法,与上述本质为一致：
        //ExecutorService pool = Executors.newFixedThreadPool(4);

        //执行任务
        for (int i = 0; i < 10; i++) {
            int index = i;
            pool.execute( ()-> System.out.println("i:"+index+"execute!"));
        }
        //关闭线程池
        pool.shutdown();
    }

    /**
     * 单例线程池
     */
    private static void pool04() {
        //创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(() -> {
                try {
                    System.out.println(index);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }
    }

    /**
     * 定时延迟执行线程池
     */
    private static void pool03() {
        //创建一个定长线程池，支持定时及周期性任务执行
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.schedule(() -> System.out.println("delay 3 seconds"), 3, TimeUnit.SECONDS);
    }

    /**
     * 可缓存线程池
     */
    private static void pool02() {
        //创建一个可缓存线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    try {
                        //打印正在执行的缓存线程信息
                        System.out.println(Thread.currentThread().getName()+"正在被执行");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 定长线程池
     */
    private static void pool01() {
        //创建一个可重用固定个数的线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
             fixedThreadPool.execute(new Runnable() {
                public void run() {
                     try {
                        //打印正在执行的缓存线程信息
                        System.out.println(Thread.currentThread().getName()+"正在被执行");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
             });
         }
    }

}
