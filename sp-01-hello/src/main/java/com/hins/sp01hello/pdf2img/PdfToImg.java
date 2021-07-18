package com.hins.sp01hello.pdf2img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.lowagie.text.pdf.PdfReader;
/**
 * @author qixuan.chen
 * @date 2020-12-05 12:47
 */
public class PdfToImg {



    public static void main(String[] args) {
        //sn33pdf();//剩女三十三
        //buyiyangdeta();//不一样的他
//        getGsdjh();//该死的家伙
       // getDtzr(); //登台之日


//        String pdfPath = "/Users/chenqixuan/Downloads/toptoon/226-完美的一半/1-5.pdf";
//        String pdfPath = "/Users/chenqixuan/Downloads/toptoon/226-完美的一半/6-10.pdf";
//        String pdfPath = "/Users/chenqixuan/Downloads/toptoon/226-完美的一半/11-20.pdf";
//        String pdfPath = "/Users/chenqixuan/Downloads/toptoon/226-完美的一半/21-23.pdf";
//        String pdfPath = "/Users/chenqixuan/Downloads/toptoon/226-完美的一半/24.pdf";
//        String pdfPath = "/Users/chenqixuan/Downloads/toptoon/226-完美的一半/25-30.pdf";
//        String pdfPath = "/Users/chenqixuan/Downloads/toptoon/226-完美的一半/31-40.pdf";
//        String pdfPath = "/Users/chenqixuan/Downloads/toptoon/226-完美的一半/41-45.pdf";
//        String pdfPath = "/Users/chenqixuan/Downloads/toptoon/226-完美的一半/46-53.pdf";
//        String pdfPath = "/Users/chenqixuan/Downloads/toptoon/226-完美的一半/54-55.pdf";
//        String pdfPath = "/Users/chenqixuan/Downloads/toptoon/226-完美的一半/56-60.pdf";
        String pdfPath = "/Users/chenqixuan/Downloads/toptoon/226-完美的一半/61-66.pdf";

        String imgPath = "/Users/chenqixuan/Downloads/toptoon";
        pdf2Image(pdfPath, imgPath, 130);

    }

    private static void getDtzr() {
        //        String pdfPath = "/Users/chenqixuan/Downloads/111-登台之日『完结』/ch01-10.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/111-登台之日『完结』/ch11-20.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/111-登台之日『完结』/21.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/111-登台之日『完结』/22.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/111-登台之日『完结』/23-24.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/111-登台之日『完结』/25.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/111-登台之日『完结』/26.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/111-登台之日『完结』/27.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/111-登台之日『完结』/28.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/111-登台之日『完结』/29.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/111-登台之日『完结』/30.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/111-登台之日『完结』/31.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/111-登台之日『完结』/32.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/111-登台之日『完结』/33.PDF";
        String pdfPath = "/Users/chenqixuan/Downloads/111-登台之日『完结』/34.PDF";
        String imgPath = "/Users/chenqixuan/Downloads/toptoon";
        pdf2Image(pdfPath, imgPath, 130);
    }

    private static void getGsdjh() {
        //        String pdfPath = "/Users/chenqixuan/Downloads/042该死的家伙￥18话第一季完结/0-10.PDF";
        String pdfPath = "/Users/chenqixuan/Downloads/042该死的家伙￥18话第一季完结/11-18.PDF";
        String imgPath = "/Users/chenqixuan/Downloads/toptoon";
        pdf2Image(pdfPath, imgPath, 130);
    }

    private static void buyiyangdeta() {
        //        String pdfPath = "/Users/chenqixuan/Downloads/110-不一样的她 1-30  （完结）/01.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/110-不一样的她 1-30  （完结）/02.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/110-不一样的她 1-30  （完结）/03.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/110-不一样的她 1-30  （完结）/04-06.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/110-不一样的她 1-30  （完结）/07-10.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/110-不一样的她 1-30  （完结）/11-15.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/110-不一样的她 1-30  （完结）/16-20.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/110-不一样的她 1-30  （完结）/21-25.PDF";
        String pdfPath = "/Users/chenqixuan/Downloads/110-不一样的她 1-30  （完结）/26-30.PDF";
        String imgPath = "/Users/chenqixuan/Downloads/toptoon";
        pdf2Image(pdfPath, imgPath, 130);
    }

    private static void sn33pdf() {
        //        String pdfPath = "/Users/chenqixuan/Downloads/002剩女三十三（25话）完结/1-10.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/002剩女三十三（25话）完结/11-20.PDF";
//        String pdfPath = "/Users/chenqixuan/Downloads/002剩女三十三（25话）完结/21-23.PDF";
        String pdfPath = "/Users/chenqixuan/Downloads/002剩女三十三（25话）完结/24.PDF";
        String imgPath = "/Users/chenqixuan/Downloads/toptoon";
        pdf2Image(pdfPath, imgPath, 130);
    }

    /***
     * PDF文件转PNG图片，全部页数
     *
     * @param PdfFilePath pdf完整路径
     * @param dstImgFolder 图片存放的文件夹
     * @param dpi dpi越大转换后越清晰，相对转换速度越慢
     * @return
     */
    public static void pdf2Image(String PdfFilePath, String dstImgFolder, int dpi) {
        File file = new File(PdfFilePath);
        PDDocument pdDocument;
        try {
            String imgPDFPath = file.getParent();
            int dot = file.getName().lastIndexOf('.');
            String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
            String imgFolderPath = null;
            if (dstImgFolder.equals("")) {
                imgFolderPath = imgPDFPath + File.separator + imagePDFName;// 获取图片存放的文件夹路径
            } else {
                imgFolderPath = dstImgFolder + File.separator + imagePDFName;
            }

            if (createDirectory(imgFolderPath)) {

                pdDocument = PDDocument.load(file);
                PDFRenderer renderer = new PDFRenderer(pdDocument);
                /* dpi越大转换后越清晰，相对转换速度越慢 */
                PdfReader reader = new PdfReader(PdfFilePath);
                int pages = reader.getNumberOfPages();
                StringBuffer imgFilePath = null;
                for (int i = 0; i < pages; i++) {
                    String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
                    imgFilePath = new StringBuffer();
                    imgFilePath.append(imgFilePathPrefix);
                    imgFilePath.append("_");
                    imgFilePath.append(String.valueOf(i + 1));
                    imgFilePath.append(".jpg");
                    File dstFile = new File(imgFilePath.toString());
                    BufferedImage image = renderer.renderImageWithDPI(i, dpi);
                    ImageIO.write(image, "jpg", dstFile);
                }
                System.out.println("PDF文档转PNG图片成功！");

            } else {
                System.out.println("PDF文档转PNG图片失败：" + "创建" + imgFolderPath + "失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean createDirectory(String folder) {
        File dir = new File(folder);
        if (dir.exists()) {
            return true;
        } else {
            return dir.mkdirs();
        }
    }


}