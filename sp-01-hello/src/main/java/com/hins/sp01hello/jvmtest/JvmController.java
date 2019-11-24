package com.hins.sp01hello.jvmtest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qixuan.chen
 * @date 2019-08-15 10:04
 */
public class JvmController {

    public static void main(String[] args){
        //查看Jvm内存 (默认占总内存的1/4(四分之一))
        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024 +"-mb");

        OOM();//模拟堆内存溢出

        //test1();//模拟栈内存溢出


    }

    /**
     * 模拟堆内存溢出
     * java.lang.OutOfMemoryError: Java heap space
     */
    private static void OOM() {
        //模拟堆内存溢出 （存储大对象、大数据，循环不停存储，或者调低内存参数）
        //byte[] byteArray = new byte[1*1024*1024*5000]; //存储4000mb的数据
        List<byte[]> buffer = new ArrayList<>();
        while(true){
            buffer.add(new byte[10*1024*1024]);
        }
    }

    /**
     * java.lang.StackOverflowError
     * 模拟栈内存溢出
     * 递归调用方法
     */
    private static void test1(){
        test1();
    }

}
