package com.hins.sp24.sp24myioc.test;

import com.hins.sp24.sp24myioc.util.ClassFileScanUtil;

import java.util.List;

public class TestClassFileScanUtil {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        //测试类加载
//        MyClassLoader classLoader=new MyClassLoader();
//        Class cls=classLoader.loadClass("D:\\project\\spring_ioc_impl\\target\\classes\\com\\humorchen\\spring_ioc_impl\\bean\\User.class","com.humorchen.spring_ioc_impl.bean.User");
//        System.out.println(cls.newInstance().toString());
        //测试扫描某个路径下的所有class文件路径
//        List<String> list=ClassFileScanUtil.scanRetFilePath(ClassFileScanUtil.getClassRootDirPath();
//        for (String s:list){
//            System.out.println(s);
//        }
        //测试扫描返回类
//        List<Class> list=ClassFileScanUtil.scanRetClass(ClassFileScanUtil.getClassRootDirPath());
//        for (Class cls:list){
//            System.out.println(cls.getName());
//        }
        //测试扫描所有类
        List<Class> list= ClassFileScanUtil.scanPackageRetClass("com");
        for (Class cls:list){
            System.out.println(cls.getName());
        }
    }
}
