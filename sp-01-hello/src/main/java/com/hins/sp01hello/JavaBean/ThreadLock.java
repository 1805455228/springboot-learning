package com.hins.sp01hello.JavaBean;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程锁
 * @author qixuan.chen
 * @date 2019-09-16 12:04
 */
public class ThreadLock {



    public static void main(String[] args){

        //func01();

        ThreadTest2 obj= new ThreadTest2();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                obj.handleNum2();//多个线程作用域同一个对象（成员变量是争夺资源）

                //每一个线程，都有一个对象（多例模式），（静态成员变量是争夺资源）
                //new ThreadTest2().handleNum();//lock
                new ThreadTest2().handleNum2();//sync类上
                //new ThreadTest2().handleNum3();//sync方法里 this
                //new ThreadTest2().handleNum4();//sync方法上

                //ThreadTest.handleNum();
            },"T"+i).start();
        }



    }

    private static void func01() {
        ThreadTest2 obj= new ThreadTest2();
        new Thread(() -> {
            obj.handleNum();
            //new ThreadTest2().handleNum();
            ThreadTest.handleNum();
        },"T1").start();

        new Thread(() -> {
            obj.handleNum();
            //new ThreadTest2().handleNum();
            ThreadTest.handleNum();
        },"T2").start();
    }


}

class ThreadTest{

    static Lock lock = new ReentrantLock();

    private static Integer num = 10;

    public static void handleNum(){
        try {
            lock.lock();
            //lock.tryLock(3, TimeUnit.SECONDS);
            if(num > 0){
                num--;
                System.out.println(Thread.currentThread().getName()+" slnum: "+num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public synchronized static void handleNum2(){
        try {
            if(num > 0){
                num--;
                System.out.println(Thread.currentThread().getName()+" ssnum: "+num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ThreadTest2{

    static Lock lock = new ReentrantLock();

    private static Integer num = 10;

    public void handleNum(){
        try {
            lock.lock();
            //lock.tryLock(3, TimeUnit.SECONDS);
            if(num > 0){
                num--;
                System.out.println(Thread.currentThread().getName()+" num: "+num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 加载类上正确
     */
    public void handleNum2(){
        synchronized (ThreadTest2.class){
            try {
                if (num > 0) {
                    num--;
                    System.out.println(Thread.currentThread().getName() + " synum: " + num);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载方法里的this
     */
    public void handleNum3(){
        synchronized (this){
            try {
                if (num > 0) {
                    num--;
                    System.out.println(Thread.currentThread().getName() + " synum: " + num);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载方法上
     */
    public synchronized void handleNum4(){
        try {
            if (num > 0) {
                num--;
                System.out.println(Thread.currentThread().getName() + " synum: " + num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






