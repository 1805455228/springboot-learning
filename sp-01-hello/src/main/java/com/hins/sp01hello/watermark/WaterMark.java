package com.hins.sp01hello.watermark;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 文档图片水印处理
 * @author : chenqixuan
 * @date : 2020/8/11
 */
public class WaterMark {
    private static List<File> fileList = new ArrayList<File>();
    public static void main(String[] args) {
        //图片所在的根目录 , 图片去除水印后的存储目录
        String dir = "D:\\github\\captcha\\images\\jigsaw\\original\\bg1.png";
        String saveDir = "D:\\github\\captcha\\images\\jigsaw\\original\\bg1111.png";
        convertAllImages(dir, saveDir); //支持批量去除图片水印
        System.out.println("去除水印");
    }
    private static void convertAllImages(String dir, String saveDir) {
        File dirFile = new File(dir);
        File saveDirFile = new File(saveDir);
        dir = dirFile.getAbsolutePath();
        saveDir = saveDirFile.getAbsolutePath();
        loadImages(new File(dir));
        for (File file : fileList) {
            String filePath = file.getAbsolutePath();
            String dstPath = saveDir + filePath.substring(filePath.indexOf(dir) + dir.length(), filePath.length());
            System.out.println("converting: " + filePath);
            replaceColor(file.getAbsolutePath(), dstPath);
        }
    }


    public static void loadImages(File f) {
        if (f != null) {
            if (f.isDirectory()) {
                File[] fileArray = f.listFiles();
                if (fileArray != null) {
                    for (int i = 0; i < fileArray.length; i++) {
                        //递归调用
                        loadImages(fileArray[i]);
                    }
                }
            } else {
                String name = f.getName();
                if (name.endsWith("png") || name.endsWith("jpg")) {
                    fileList.add(f);
                }
            }
        }
    }

    private static void replaceFolderImages(String dir) {
        File dirFile = new File(dir);
        File[] files = dirFile.listFiles(new FileFilter() {
            public boolean accept(File file) {
                String name = file.getName();
                if (name.endsWith("png") || name.endsWith("jpg")) {
                    return true;
                }
                return false;
            }
        });
        for (File img : files) {
            replaceColor(img.getAbsolutePath(), img.getAbsolutePath());
        }
    }

    private static void replaceColor(String srcFile, String dstFile) {
        try {
            Color color = new Color(255, 195, 195);
            replaceImageColor(srcFile, dstFile, color, Color.WHITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void replaceImageColor(String file, String dstFile, Color srcColor, Color targetColor) throws IOException {
        URL http;
        //trim()是确定去掉指定的空白字符串，starsWith指的是是否以指定的头文件开始
        if (file.trim().startsWith("https")) {
            http = new URL(file);
            HttpsURLConnection conn = (HttpsURLConnection) http.openConnection();
            conn.setRequestMethod("GET");
        } else if (file.trim().startsWith("http")) {
            http = new URL(file);
            HttpURLConnection conn = (HttpURLConnection) http.openConnection();
            conn.setRequestMethod("GET");
        } else {
            http = new File(file).toURI().toURL();
        }

        //BufferImage指的是将图片加载到内存
        BufferedImage bi = ImageIO.read(http.openStream());
        if(bi == null){
            return;
        }

        //Color wColor = new Color(255, 255, 255);//白色
//        Color wColor = new Color(238, 243, 249);//浅灰色
        Color wColor = new Color(234, 234, 234, 80);//浅灰色
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                //System.out.println(bi.getRGB(i, j));
                int color = bi.getRGB(i, j);//getRGB（）返回的是整型的像素
                Color oriColor = new Color(color);
                int red = oriColor.getRed();
                int greed = oriColor.getGreen();
                int blue = oriColor.getBlue();
//粉色
//                    if (greed < 190 || blue < 190) {
//
//                    } else {
                    //去掉粉色水印(粉色替换为白色)
                    //	if (red == 255 && greed > 180 && blue > 180) {
                    //	bi.setRGB(i, j, wColor.getRGB());
                    //	}
                    //去掉灰色水印（灰色替换为白色）
                    //	if (red == 229 && greed == 229 && blue == 229) {
                    //	bi.setRGB(i, j, wColor.getRGB());
                    //	}
                    //去掉浅灰色水印（灰色替换为白色或替换为浅灰色）
                if (red >170 && greed > 170 && blue > 170) {
                    bi.setRGB(i, j, wColor.getRGB());
                }
//                    }
            }
        }





        String type = file.substring(file.lastIndexOf(".") + 1, file.length());
        Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName(type);
        ImageWriter writer = it.next();
        File f = new File(dstFile);
        f.getParentFile().mkdirs();
        ImageOutputStream ios = ImageIO.createImageOutputStream(f);
        writer.setOutput(ios);
        writer.write(bi);
        bi.flush();
        ios.flush();
        ios.close();
    }


    /**
     * 添加文字水印
     * @param targetImg 目标图片路径，如：C://myPictrue//1.jpg
     * @param pressText 水印文字， 如：中国证券网
     * @param fontName 字体名称，	如：宋体
     * @param fontStyle 字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
     * @param fontSize 字体大小，单位为像素
     * @param color 字体颜色
     * @param x 水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y 水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha 透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
    public static void pressText(String targetImg, String pressText, String fontName, int fontStyle, int fontSize, Color color, int x, int y, float alpha) {
        try {
            File file = new File(targetImg);

            String PICTRUE_FORMATE_JPG = targetImg.substring(targetImg.lastIndexOf(".")+1,targetImg.length());

            Image image = ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setColor(color);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            int width_1 = fontSize * getLength(pressText);
            int height_1 = fontSize;
            int widthDiff = width - width_1;
            int heightDiff = height - height_1;
            if(x < 0){
                x = widthDiff / 2;
            }else if(x > widthDiff){
                x = widthDiff;
            }
            if(y < 0){
                y = heightDiff / 2;
            }else if(y > heightDiff){
                y = heightDiff;
            }

            g.drawString(pressText, x, y + height_1);
            g.dispose();
            ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
     * @param text
     * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
     */
    public static int getLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                length++;
            }
        }
        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
    }
}
