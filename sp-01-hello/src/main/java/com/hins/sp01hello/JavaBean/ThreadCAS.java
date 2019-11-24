package com.hins.sp01hello.JavaBean;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author qixuan.chen
 * @date 2019-09-16 16:54
 */
public class ThreadCAS {

    /**
     *  * CAS（Compare and swap），即比较并交换，也是实现我们平时所说的自旋锁或乐观锁的核心操作。
     *  * 它的实现很简单，就是用一个预期的值和内存值进行比较，如果两个值相等，就用预期的值替换内存值，
     *  * 并返回 true。否则，返回 false。
     */
    private static AtomicBoolean b = new AtomicBoolean(true);

    private static AtomicInteger a = new AtomicInteger(1);

    //解决ABA
    private static AtomicStampedReference<Integer> atomicStampedRef = new AtomicStampedReference<>(1, 0);

    private static int tickTotal = 10;//抢票系统（zxcas编号的列车票数）

    public static void main(String[] args) {

        //func01();
        ThreadCAS obj = new ThreadCAS();
        //线程争夺，同一个对象的资源

        //多线程抢票
        for (int i = 0; i <7 ; i++) {
            new Thread(() ->{

                try {
                    Thread.sleep(2000);

                    //obj.buyTick();
                    int n = 0;
                    obj.buyTickAndTry(n);//有自旋
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"A类线程"+i).start();
        }

        for (int j = 0; j <5 ; j++) {
            new Thread(() ->{
                try {
                    Thread.sleep(1000);//新对象2
                    //obj.buyTick();
                    int n = 0;
                    obj.buyTickAndTry(n);//有自旋
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"B类线程"+j).start();
        }

    }

    private static void func01() {
        Thread main = new Thread(() ->{
            ThreadCAS obj = new ThreadCAS();
            obj.buyTick();
        },"主线程");

        Thread other = new Thread(new Runnable() {
            @Override
            public void run() {
                ThreadCAS obj = new ThreadCAS();
                obj.buyTick();
            }
        },"干扰线程");

        main.start();
        other.start();
    }

    /**
     * 买车票业务
     * @return
     */
    public String buyTick(){
        // 加锁（乐观锁）
        boolean lock = b.compareAndSet(true,false);
        if(lock){
            if(tickTotal<=0){
                System.out.println(Thread.currentThread().getName()+"今天车票卖光了，明天再来");
                return "no";
            }
            //抢到车票
            tickTotal--;
            System.out.println(Thread.currentThread().getName()+"。。。。。。。。。。。。。剩余车票："+tickTotal);
            System.out.println("购票成功");
            b.set(true);//释放锁
            return "success";
        }else{
            //获取不到锁,不进行重试机制（看业务情况，一般不进行重试）
            System.out.println(Thread.currentThread().getName()+"====重试：");
            return "error";
        }
    }

    /**
     * 买车票业务（有重试机制）
     * @return
     */
    public String buyTickAndTry(int n){
        // 加锁（乐观锁）
        boolean lock = b.compareAndSet(true,false);

        //取不到锁的情况下，重试循坏（取到锁lock为true，取不到为false）
        while(!lock){
            if(n>=5){
                break;
            }
            n++;
            //拿不到锁的线程，不断的在死循环
            System.out.println(Thread.currentThread().getName()+"====重试：");

        }

        if(tickTotal<=0){
            System.out.println(Thread.currentThread().getName()+"今天车票卖光了，明天再来");
            return "no";
        }
        //抢到车票
        tickTotal--;
        System.out.println(Thread.currentThread().getName()+"。。。。。。。。。。。。。剩余车票："+tickTotal);
        System.out.println("购票成功");
        b.set(true);//释放锁
        return "success";
    }

    /**
     * 买车票业务（自旋）
     * @return
     */
    public String buyTickAndTry2(){
        // 加锁（乐观锁）
        boolean lock = b.compareAndSet(true,false);

        //取不到锁的情况下，重试循坏（取到锁lock为true，取不到为false）
        while(!lock){
            //拿不到锁的线程，不断的在死循环
            System.out.println(Thread.currentThread().getName()+"====重试：");
        }

        if(tickTotal<=0){
            System.out.println(Thread.currentThread().getName()+"今天车票卖光了，明天再来");
            return "no";
        }
        //抢到车票
        tickTotal--;
        System.out.println(Thread.currentThread().getName()+"。。。。。。。。。。。。。剩余车票："+tickTotal);
        System.out.println("购票成功");
        b.set(true);//释放锁
        return "success";
    }

}
