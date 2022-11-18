package com.hins.sp24.sp24myioc.util;

import com.hins.sp24.sp24myioc.classloader.MyClassLoader;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 扫描某个目录下的所有class文件工具
 */
public class ClassFileScanUtil {
    /**
     * 扫描出一个目录下所有的class文件
     * @param directory
     * @return
     */
    public static List<String> scanRetFilePath(String directory){
        List<String> classes=new ArrayList<String>();
        File root=new File(directory);
        if(root.isDirectory()){
            File[] files=root.listFiles();
            for(File file:files){
                if(file.isDirectory()){
                    //如果是目录则递归
                    classes.addAll(scanRetFilePath(file.getAbsolutePath()));
                }else if(file.isFile()&&file.getName().endsWith(".class")){
                    //符合则加入
                    classes.add(file.getAbsolutePath());
                }
            }
        }
        return classes;
    }
    public static List<Class> scanPackageRetClass(String packageName){
        String rootDir=getClassRootDirPath();
        MyClassLoader classLoader=new MyClassLoader();
        List<String> classes=scanRetFilePath(rootDir);
        List<Class> ret=new ArrayList<Class>();
        for(String cls:classes){
            String name=getClassNameByAbsolutePath(cls,rootDir);
            if(name.startsWith(packageName)){
                Class loadedClass=classLoader.loadClass(cls,name);
                ret.add(loadedClass);
            }
        }
        return ret;
    }
    /**
     * 对某个目录下的所有类扫描出来并返回
     * @param directory
     * @return
     */
    public static List<Class> scanRetClass(String directory){
        File root=new File(directory);
        String prefix=root.getAbsolutePath();
        List<String> filesPath=scanRetFilePath(directory);
        List<Class> ret=new ArrayList<Class>();
        MyClassLoader classLoader=new MyClassLoader();
        for(String path:filesPath){
            if(path.startsWith(prefix)){
                System.out.println(path);
                String name=getClassNameByAbsolutePath(path,prefix);
                System.out.println(name);
                Class cls=classLoader.loadClass(path,name);
                if(cls!=null){
                    ret.add(cls);
                }
            }
        }
        return ret;
    }

    /**
     * 获得class存放的目录的绝对路径
     * @return
     */
    public static String getClassRootDirPath(){
        return new File(URLDecoder.decode(ClassFileScanUtil.class.getClassLoader().getResource("").getPath())).getAbsolutePath();
    }

    /**
     * 给定一个类的完整路径和项目源码根路径，生成类名
     * @param absolutePath
     * @param srcDirPath
     * @return
     */
    public static String getClassNameByAbsolutePath(String absolutePath,String srcDirPath){
        String name=absolutePath.replace(srcDirPath+File.separator,"").replace(File.separator,".");
        name=name.substring(0,name.length()-6);
        return name;
    }

}
