package com.hins.sp01hello.jvmtest;

/**
 * @author qixuan.chen
 * @date 2019-08-23 12:01
 */
public class fibonacci {

    public static void main(String[] args){
        int i = 8;
        System.out.println(Foo(i));
    }

    /**
     * 斐波那契数列 (递归实现)
     * @param i
     * @return
     */
    public static int Foo(int i) {
        if (i <= 0)
            return 0;
        else if (i > 0 && i <= 2)
            return 1;
        else return Foo(i - 1) + Foo(i - 2);
    }
}
