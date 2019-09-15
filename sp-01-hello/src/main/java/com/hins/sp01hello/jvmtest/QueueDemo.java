package com.hins.sp01hello.jvmtest;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author qixuan.chen
 * @date 2019-09-04 14:54
 */
public class QueueDemo {

    private static Queue<String> queue = new ArrayBlockingQueue<String>(3);

    private static List<String> list = new ArrayList<>();

    public static void main(String[] args){

        listFun();

        //queueFun();
    }

    private static void listFun() {
        System.out.println("请输入：");
        for (int i=1;i<=3;i++){
            list.add("0");
        }

        while (true){
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            if(!list.contains(str)){
                list.remove(0);//移除最先输入的
            }else{
                list.remove(str);//移除重复删除的
            }
            list.add(str);//重新压进队列

            for (String s:list) {
                System.out.println("======"+s);
            }
        }
    }

    /**
     * 队列实现
     */
    private static void queueFun() {
        System.out.println("请输入：");
        for (int i=1;i<=3;i++){
            queue.add("0");
        }

        while (true){
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            if(!queue.contains(str)){
                queue.remove();//移除最先输入的
            }else{
                queue.remove(str);//移除重复删除的
            }
            queue.add(str);//重新压进队列

            for (String s:queue) {
                System.out.println("======"+s);
            }
        }
    }


}
