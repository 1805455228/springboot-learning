package com.hins.sp12fastdfs.controller;

import com.hins.sp12fastdfs.fastdfs.FastdfsClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author qixuan.chen
 * @date 2019-08-08 21:48
 */
@RestController
public class UploadFileController {

    @Autowired
    private FastdfsClientUtil fastdfsClientUtil;


    /**
     * 上传文件
     * @return
     */
    @RequestMapping("/upload/file")
    public String uploadFile(){
        File file = new File("/Users/chenqixuan/Downloads/logo.png");
        String url=null;
        try {
            url = fastdfsClientUtil.uploadFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 上传文件
     * @return
     */
    @GetMapping("/upload/str")
    public String uploadStrFile(@RequestParam("content") String content,@RequestParam("ext") String ext ){

        String url=null;
        try {
            url = fastdfsClientUtil.uploadFile(content,ext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 下载文件
     * @return
     */
    @GetMapping("/download/file")
    public void downloadFile(@RequestParam("fileUrl") String fileUrl ){

        String url=null;
        try {
           byte[] content = fastdfsClientUtil.download(fileUrl);
            File file = new File("filename.png");
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 删除文件
     * @return
     */
    @GetMapping("/upload/delete")
    public String uploadStrFile(@RequestParam("fileUrl") String fileUrl ){

        try {
             fastdfsClientUtil.deleteFile(fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success: "+fileUrl;
    }
}
