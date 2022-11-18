package com.hins.sp24.sp24myioc.test;

import com.hins.sp24.sp24myioc.bean.User;

import java.io.IOException;

public class TestClassPath {
    void test(){
        User user=new User();
        String path=user.getClass().getClassLoader().getResource(".").getPath();
        System.out.println(path);;
    }
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        new TestClassPath().test();
//        path= URLDecoder.decode(path);
    }
}
