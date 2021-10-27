package videoDownload;

import bigFIle.ConcurrentReadFile;
import bigFIle.FileHandle;
import bigFIle.IndexCalculate;
import bigFIle.IndexPair;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qixuan.chen
 * @date 2021/10/24 20:28
 */
@Slf4j
public class VideoDownload {

    public static void main(String[] args) {

        String filePath = "/Volumes/ws/txt/ks.txt";


        try {
            readFileTxt(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void readFileTxt(String filePath) throws Exception {
        long startTime1 = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        //Set<String> set = new HashSet<>(200_000_000);
        String line;
        while ((line = br.readLine()) != null) {
            //set.add(line);
        }
        br.close();
        System.out.println(String.format("BufferedReader read file cost time : %s", System.currentTimeMillis() - startTime1));

        File file = new File(filePath);
        List<IndexPair> indexPairs = IndexCalculate.getIndex(file, 64);
        System.out.println(String.format("file index size : %s, result: %s", indexPairs.size(), indexPairs));
        long startTime2 = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger();
        ConcurrentReadFile concurrentReadFile = new ConcurrentReadFile(filePath, indexPairs, new FileHandle() {
            @Override
            public void handle(String videoUrl) {
                //TODO 业务处理 2614
                try {
                    count.incrementAndGet();
                    //log.info(count +"下载视频：{}",videoUrl);
                    downloadVideoKs(videoUrl);
                } catch (Exception e) {
                    System.out.println("【下载失败】异常: exception: ");
                    e.printStackTrace();
                }
            }
        });
        concurrentReadFile.readFile();
        concurrentReadFile.end();
        System.out.println(String.format("ConcurrentReadFile read file cost time : %s, count:%s", System.currentTimeMillis() - startTime2, count.get()));
    }


    /**
     * 下载
     * @param fileUrl
     * @return
     */
    public static void downloadVideoKs(String fileUrl) {
        long l = 0L;
        String savePath = null;
        String staticAndMksDir = null;
        if (null != fileUrl) {
            try {
                //下载时文件名称
                String fileName = ".mp4";
                if(fileUrl.contains(".mp4") || fileUrl.contains(".MP4")){
                    fileName = fileUrl.substring(fileUrl.lastIndexOf("."));
                }
                //String dataStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
                String uuidName = UUID.randomUUID().toString().replace("-","");
                //path = "resources/images/"+dataStr+"/"+uuidName+fileName;
                savePath = "/Volumes/ws/ks_res/"+uuidName+fileName;
                System.out.println(String.format("保存视频路径 : %s", savePath));
                //staticAndMksDir = Paths.get(ResourceUtils.getURL("classpath:").getPath(),"resources", "images",dataStr).toString();
                HttpUtil.downloadFile(fileUrl, savePath);
            } catch (Exception e) {
                System.out.println("下载失败："+fileUrl);
                e.printStackTrace();
            } finally {

            }
        }
    }
}
