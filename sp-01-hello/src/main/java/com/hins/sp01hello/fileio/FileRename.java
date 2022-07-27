package com.hins.sp01hello.fileio;

import com.hins.sp01hello.translation_font.ZHConverter;

import java.io.File;
import java.io.IOException;

/**
 * 批量重命名文件
 * @author qixuan.chen
 * @date 2020-06-06 13:04
 */
public class FileRename {


//    static String newString = "";//新字符串,如果是去掉前缀后缀就留空，否则写上需要替换的字符串
//    static String oldString = "home.cnblogs.comu";//要被替换的字符串
    static String dir = "/Users/chenqixuan/Downloads/科普/下载";//文件所在路径，所有文件的根目录，记得修改为你电脑上的文件所在路径
//    static String dir = "/Users/chenqixuan/Downloads/童话故事";//文件所在路径，所有文件的根目录，记得修改为你电脑上的文件所在路径
//    static String dir = "/Users/chenqixuan/Downloads/历史碎片";//文件所在路径，所有文件的根目录，记得修改为你电脑上的文件所在路径

    public static void main(String[] args) throws IOException {

        recursiveTraversalFolder(dir);//递归遍历此路径下所有文件夹
    }
    /**
     * 递归遍历文件夹获取文件
     */
    public static void recursiveTraversalFolder(String path) {
        File folder = new File(path);
        if (folder.exists()) {
            File[] fileArr = folder.listFiles();
            if (null == fileArr || fileArr.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                File newDir = null;//文件所在文件夹路径+新文件名
                String newName = "";//新文件名
                String fileName = null;//旧文件名
                File parentPath = new File("");//文件所在父级路径
                for (File file : fileArr) {
                    if (file.isDirectory()) {//是文件夹，继续递归，如果需要重命名文件夹，这里可以做处理
                        System.out.println("文件夹:" + file.getAbsolutePath() + "，继续递归！");
                        recursiveTraversalFolder(file.getAbsolutePath());
                    } else {//是文件，判断是否需要重命名
                        fileName = file.getName();
                        String ext = fileName.substring(fileName.lastIndexOf("."),fileName.length());
                        parentPath = file.getParentFile();

                        newName = fileName.replaceAll("[，。_.、|~！@#￥；【》‘“”、】《·\\-？：，：{}%……&*（）\\=—+ A-Za-z0-9]","").trim();


                        ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
                        //繁体转换简体
                        newName = converter.convert(newName);


                        newName = newName + ext;
                        newDir = new File(parentPath + "/" + newName);//文件所在文件夹路径+新文件名
                        file.renameTo(newDir);//重命名
                        System.out.println("修改后：" + newDir);

//                        if (fileName.contains(oldString)) {//文件名包含需要被替换的字符串
//                            newName = fileName.replaceAll(oldString, newString);//新名字
//                            newDir = new File(parentPath + "/" + newName);//文件所在文件夹路径+新文件名
//                            file.renameTo(newDir);//重命名
//                            System.out.println("修改后：" + newDir);
//                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }
}

