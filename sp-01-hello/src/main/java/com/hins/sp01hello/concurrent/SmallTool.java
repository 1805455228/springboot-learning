package com.hins.sp01hello.concurrent;
import java.util.StringJoiner;
import java.util.concurrent.ForkJoinPool;
/**
 * 线程日志格式化打印工具类
 * @author qixuan.chen
 * @date 2022/6/22 21:29
 */
public class SmallTool {
    public static void sleepMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printTimeAndThread(String tag) {
        String result = new StringJoiner("\t|\t")
                .add(String.valueOf(System.currentTimeMillis()))
                .add(String.valueOf(Thread.currentThread().getId()))
                .add(Thread.currentThread().getName())
                .add(tag)
                .toString();
        System.out.println(result);
    }

    public static void main(String[] args) {

        // 可用的线程数
        System.out.println(Runtime.getRuntime().availableProcessors());
        // 当前线程数
        System.out.println(ForkJoinPool.commonPool().getPoolSize());
        // 最大线程数
        System.out.println(ForkJoinPool.getCommonPoolParallelism());
    }

}
