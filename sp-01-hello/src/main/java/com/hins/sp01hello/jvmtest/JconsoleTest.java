package com.hins.sp01hello.jvmtest;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Box.Filler;

/**
 * @author qixuan.chen
 * @date 2019-08-15 11:31
 */
public class JconsoleTest {
    public byte[] b1 = new byte[128*1024];
    //测试内存的变化情况
    public static void main(String[] args) {
        try {
            Thread.sleep(5000);//为了能看到效果
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("start..");
        fill(1000);
    }


    public static void fill(int n) {
        List<JconsoleTest> jlist = new ArrayList<>();
        for(int i = 0; i < n; i++){
            try {
                Thread.sleep(100);//为了能看到效果
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            jlist.add(new JconsoleTest());
        }

    }
}
