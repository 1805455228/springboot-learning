package com.hins.sp01hello.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author qixuan.chen
 * @date 2019-07-31 17:14
 */
public class CrunchifyConcurrentHashMapVsSynchronizedMap {

    public final static int THREAD_POOL_SIZE = 5;

    public static Map<String, Integer> crunchifyHashTableObject = null;
    public static Map<String, Integer> crunchifySynchronizedMapObject = null;
    public static Map<String, Integer> crunchifyConcurrentHashMapObject = null;

    /**
     * 存储100,000 (10万)数据时
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        // Hashtable 线程安全 效率低
        crunchifyHashTableObject = new Hashtable<>();
        crunchifyPerformTest(crunchifyHashTableObject);

        // synchronizedMap 线程安全 效率低
        crunchifySynchronizedMapObject = Collections.synchronizedMap(new HashMap<>());
        crunchifyPerformTest(crunchifySynchronizedMapObject);

        // ConcurrentHashMap 线程安全 效率高
        crunchifyConcurrentHashMapObject = new ConcurrentHashMap<>();
        crunchifyPerformTest(crunchifyConcurrentHashMapObject);

    }

    public static void crunchifyPerformTest(final Map<String, Integer> crunchifyThreads) throws InterruptedException {

        System.out.println("Test started for: " + crunchifyThreads.getClass());
        long averageTime = 0;
        for (int i = 0; i < 5; i++) {

            long startTime = System.nanoTime();
            //创建线程池
            ExecutorService crunchifyExServer = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

            for (int j = 0; j < THREAD_POOL_SIZE; j++) {
                crunchifyExServer.execute(new Runnable() {
                    @SuppressWarnings("unused")
                    @Override
                    public void run() {

                        for (int i = 0; i < 100000; i++) {
                            Integer crunchifyRandomNumber = (int) Math.ceil(Math.random() * 550000);

                            // Retrieve value. We are not using it anywhere
                            Integer crunchifyValue = crunchifyThreads.get(String.valueOf(crunchifyRandomNumber));

                            // Put value
                            crunchifyThreads.put(String.valueOf(crunchifyRandomNumber), crunchifyRandomNumber);
                        }
                    }
                });
            }

            // Make sure executor stops
            crunchifyExServer.shutdown();

            // Blocks until all tasks have completed execution after a shutdown request
            crunchifyExServer.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

            long entTime = System.nanoTime();
            long totalTime = (entTime - startTime) / 1000000L;
            averageTime += totalTime;
            System.out.println("2500K entried added/retrieved in " + totalTime + " ms");
        }
        System.out.println("For " + crunchifyThreads.getClass() + " the average time is " + averageTime / 5 + " ms\n");
    }
}
