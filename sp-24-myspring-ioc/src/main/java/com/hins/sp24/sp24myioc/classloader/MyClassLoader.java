package com.hins.sp24.sp24myioc.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * 我的类加载器
 */
public class MyClassLoader extends ClassLoader {
    private static String MINE=MyClassLoader.class.getName();
    /**
     * 传入绝对路径则  loadClass("d:\\project\\spring_ioc_impl\\target\\classes\\com\\humorchen\\spring_ioc_impl\\annotation\\MyAutowired.class",false)
     * @param absolutePath 绝对路径
     * @return
     */
    public Class loadClass(String absolutePath,String name){
        //如果已经加载了直接返回
        try {
            Class cls=MyClassLoader.class.getClassLoader().loadClass(name);
            if(cls!=null){
                return cls;
            }
        }catch (Exception e){}
        //没有加载的继续加载
        byte[] data=loadData(absolutePath);
        return this.defineClass(name,data,0,data.length);
    }

    private byte[] loadData(String path){
        try {
            FileInputStream inputStream=new FileInputStream(path);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            int b;
            while ((b=inputStream.read())!=-1){
                byteArrayOutputStream.write(b);
            }
            inputStream.close();
            return byteArrayOutputStream.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
