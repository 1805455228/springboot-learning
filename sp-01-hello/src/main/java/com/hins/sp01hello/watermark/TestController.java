package com.hins.sp01hello.watermark;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

/**
 * @author : chenqixuan
 * @date : 2020/8/11
 */
public class TestController {

    /**
     * 给图片添加水印
     *
     * @param filePath
     *            需要添加水印的图片的路径
     * @param markContent
     *            水印的文字
     * @param markContentColor
     *            水印文字的颜色
     * @param qualNum
     *            图片质量
     * @return
     */
    public static boolean createMark(String filePath, String markContent,
                              Color markContentColor, float qualNum) {
        ImageIcon imgIcon = new ImageIcon(filePath);
        Image theImg = imgIcon.getImage();
        int width = theImg.getWidth(null);
        int height = theImg.getHeight(null);
        BufferedImage bimage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();
        g.setColor(markContentColor);
        g.setBackground(Color.white);
        g.drawImage(theImg, 0, 0, null);
        g.drawString(markContent, width / 5, height / 5); // 添加水印的文字和设置水印文字出现的内容
        g.dispose();
        try {
            FileOutputStream out = new FileOutputStream(filePath);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
            param.setQuality(qualNum, true);
            encoder.encode(bimage, param);
            out.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
//        String path = "C:\\Users\\chensj.DESKTOP-UU02GN3\\Desktop\\20200526214539688.png";
        String path = "D:\\github\\captcha\\images\\jigsaw\\original\\bg1.png";
        String markContent = "www.360.cn/hhf/index";
        Color markContentColor = new Color(234, 234, 234, 80);
        float qualNum = 1;
        TestController.createMark(path,markContent,markContentColor,qualNum);
        System.out.println("水印创建成功");
    }
}