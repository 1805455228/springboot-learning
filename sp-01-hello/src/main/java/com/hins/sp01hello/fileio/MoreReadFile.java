package com.hins.sp01hello.fileio;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : chenqixuan
 * @date : 2021/10/25
 */
public class MoreReadFile {

    private static ThreadPoolExecutor pool;

    public static void main(String[] args) {

        String filePath = "";

        readFile(filePath);
    }


    private static void readFile(String localFilePath){
        pool = new ThreadPoolExecutor(10, 20, 0L,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

        File file = new File(localFilePath);

        while (true){

        }

    }

    private void downloadFile(String videoUrl){
        pool.execute( ()-> {
            System.out.println("下载文件");
        });
    }
}
