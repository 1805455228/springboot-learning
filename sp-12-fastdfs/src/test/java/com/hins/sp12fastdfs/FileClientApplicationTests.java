package com.hins.sp12fastdfs;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author qixuan.chen
 * @date 2019-08-07 23:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileClientApplicationTests {

    @Autowired
    private FastFileStorageClient storageClient;


    @Test
    public void testUpload() throws FileNotFoundException {
        File file = new File("/Users/chenqixuan/Downloads/logo.png");
        // 上传并且生成缩略图
        StorePath storePath = this.storageClient.uploadFile(
                new FileInputStream(file), file.length(), "png", null);
        // 带分组的路径
        System.out.println(storePath.getFullPath());
        // 不带分组的路径
        System.out.println(storePath.getPath());
    }

    @Test
    public void testUploadAndCreateThumb() throws FileNotFoundException {
        File file = new File("/Users/chenqixuan/Downloads/logo.png");
        // 上传并且生成缩略图
        StorePath storePath = this.storageClient.uploadFile(
                new FileInputStream(file), file.length(), "png", null);
        // 带分组的路径
        System.out.println(storePath.getFullPath());
        // 不带分组的路径
        System.out.println(storePath.getPath());
    }

    @Test
    public void del() throws FileNotFoundException {
        String fileUrl = "group1/M00/00/00/wKgBCl1MJy2ASt2qAAAQUldiNbQ339.png";
        StorePath storePath = StorePath.praseFromUrl(fileUrl);
        storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        //storageClient.deleteFile(fileUrl);
        System.out.println("删除成功："+fileUrl);
    }

}