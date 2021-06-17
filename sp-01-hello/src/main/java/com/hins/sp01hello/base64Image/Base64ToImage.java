package com.hins.sp01hello.base64Image;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * @author : chenqixuan
 * @date : 2021/6/17
 */
@Slf4j
public class Base64ToImage {


    public static String imageUrl = "https://fendaiyh.com/xxdist/b88097/c8097003/img1-8a2e5825bf11e73d2a8bbe7498df4420.jpg";


    public static void main(String[] args) {

        imageUrl = imageUrl.replace(".jpg",".html");
        String filePath = "D:\\xuan\\工作项目文件\\websocket-html\\" + "xxxxx.html";
        HttpTool httpTool = new HttpTool();
        httpTool.downloadPicture(imageUrl,filePath);

    }







    /**
     * base64转化成图片文件
     * @param base64
     * @param imgFilePath
     * @return
     */
    public static boolean generateImage(String base64, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
        if (base64 == null) // 图像数据为空
            return false;
        try {
            // Base64解码
            byte[] bytes = Base64.getDecoder().decode(base64);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
