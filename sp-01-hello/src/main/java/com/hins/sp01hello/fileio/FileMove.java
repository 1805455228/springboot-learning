package com.hins.sp01hello.fileio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author qixuan.chen
 * @date 2020-08-29 15:36
 */
public class FileMove {

    static String dir = "/Users/chenqixuan/Downloads/科普/toptoon/HM050/cznh";//文件所在路径，所有文件的根目录，记得修改为你电脑上的文件所在路径
    static String targetDir = "/Users/chenqixuan/Downloads/科普/toptoon/HM050/image";//文件所在路径，所有文件的根目录，记得修改为你电脑上的文件所在路径

    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值

    static String newFile = "0";

    static int fileNum = 0;

    public static void main(String[] args) {
//        File fold = new File(dir);//某路径下的文件
//        String strNewPath = "e://java//new file//";//新路径
//        File fnewpath = new File(strNewPath);
//        if(!fnewpath.exists())
//            fnewpath.mkdirs();
//        File fnew = new File(strNewPath+fold.getName());
//        fold.renameTo(fnew);

        folderMethod2(dir);

        System.out.println("章节数："+fileNum);
    }


    /**
     * 递归方式
     * @param path
     */
    public synchronized static void folderMethod2(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null != files) {
                List<File> fileList = new ArrayList<>();
                for (File f : files) {
                    fileList.add(f);
                }

                Collections.sort(fileList, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        if (o1.isDirectory() && o2.isFile()){
                            return -1;
                        }
                        if (o1.isFile() && o2.isDirectory()){
                            return 1;
                        }
                        Integer f = fileNameSort(o1.getName());
                        Integer f2 = fileNameSort(o2.getName());
                        return Integer.compare(f, f2);
                    }
                });

                for (File file2 : fileList) {

                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath()+"======"+file2.getName());
                        folderMethod2(file2.getAbsolutePath());

                    } else {
                        long orfileSize = file2.length();
                        double size = FormetFileSize(orfileSize,2);
                        int fileSize = (int) Math.ceil(size);
                        System.out.println("文件:" + file2.getAbsolutePath() + "======"+fileSize);

                        if(fileSize == 60 || fileSize == 61  || fileSize == 51){
                            fileNum++;
//                            newFile = targetDir + "/ch"+fileNum;
                            newFile = targetDir + "/"+fileNum;
                        }

                        File fnewpath = new File(newFile);
                        if(!fnewpath.exists())
                            fnewpath.mkdirs();
                        File fnew = new File(fnewpath+"/"+file2.getName());
                        try {
                            copy(file2,fnew);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    /**
     * 转换文件大小,指定转换的类型
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    private static void copy(File oldpath, File newpath) throws IOException {
        // TODO Auto-generated method stub
        if (!newpath.exists()) {
            Files.copy(oldpath.toPath(), newpath.toPath());
        } else {
            newpath.delete();
            Files.copy(oldpath.toPath(), newpath.toPath());
        }
    }


    static Integer fileNameSort(String filename) {
        String string2 = filename;
        if(filename.contains(".")){
            int x = filename.indexOf(".");
            string2 = filename.substring(0,x);
        }
        char[] cs = string2.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cs.length; i++) {
            if(Character.isDigit(cs[i])) {
                builder.append(cs[i]);
            }
        }
        return Integer.parseInt(builder.toString());
    }

}
