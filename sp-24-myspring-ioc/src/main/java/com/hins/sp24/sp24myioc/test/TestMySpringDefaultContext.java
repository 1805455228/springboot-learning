package com.hins.sp24.sp24myioc.test;

import com.hins.sp24.sp24myioc.bean.User;
import com.hins.sp24.sp24myioc.context.MySpringDefaultContext;

public class TestMySpringDefaultContext {
    public static void main(String[] args) {
        MySpringDefaultContext context= new MySpringDefaultContext();
        context.overview();
        User user=(User) context.getBean(User.class);
        System.out.println("第一次获得user "+user);
        System.out.println("设置年龄18");
        user.setAge(18);
        System.out.println("打印user "+user);
        System.out.println("重新获取一个作为user1 ");
        User user1=(User) context.getBean(User.class);
        System.out.println("user1的数据 "+user1.toString());
        user1.setAge(154);
        System.out.println("将user1年龄更改");
        System.out.println("user的数据 "+user.toString()+"  user1的数据 "+user1.toString());
        context.overview();
    }
}
