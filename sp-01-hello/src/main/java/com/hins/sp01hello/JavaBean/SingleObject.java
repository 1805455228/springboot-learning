package com.hins.sp01hello.JavaBean;

/**
 * 单例模式-双端检测
 * @author qixuan.chen
 * @date 2019-09-15 09:47
 */
public class SingleObject {

    //在类加载到内存时候静态成员变量就存在了，而对象还不存在，volatile内存可见（防止指令重排，出现）
    private static volatile SingleObject bean = null;

    //将该类的构造函数私有化（目的是禁止其他程序创建该类的对象）
    private SingleObject() {
        System.out.println("单列new 对象："+Thread.currentThread().getName());
    }

    //双端检测，成员方法必须是静态，（构造私有化，其他类不能new它的对象）
    public static SingleObject getBean(){
        if(null == bean){
            synchronized (SingleObject.class){
                if(null == bean){
                    bean = new SingleObject();
                }
            }
        }
        return bean;
    }
}

