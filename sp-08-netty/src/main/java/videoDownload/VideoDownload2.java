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
public class VideoDownload2 {

    public static void main(String[] args) {

        String filePath = "C:\\Users\\USER\\Downloads\\xjj-master\\xjj-master\\video\\ks.txt";
//        String filePath = "/Volumes/ws/txt/ks.txt";


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
        Integer threadSize = 5;
        List<IndexPair> indexPairs = IndexCalculate.getIndex(file, threadSize);
        System.out.println(String.format("file index size : %s, result: %s", indexPairs.size(), indexPairs));
        long startTime2 = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger();
        ConcurrentReadFile concurrentReadFile = new ConcurrentReadFile(filePath, indexPairs, new FileHandle() {
            @Override
            public void handle(String videoUrl) {
                count.incrementAndGet();
                //TODO 业务处理 2614
                try {

                    //System.out.println(String.format("线程：%s，行号：%s 下载视频 : %s ",Thread.currentThread().getName(),count, videoUrl));
                    //log.info(count +"下载视频：{}",videoUrl);
                    String outPutFilePath = "D:\\temp\\test.txt";
                    //writeFIleTxt(outPutFilePath,videoUrl);
                    if(5==count.get()){
                        downloadVideoKs(count.get(),videoUrl);
                    }

                } catch (Exception e) {
                    //log.error("【下载失败】异常: exception: {}", e);
                    e.printStackTrace();
                }
            }
        });
        concurrentReadFile.readFile();
        concurrentReadFile.end();
        System.out.println(String.format("ConcurrentReadFile read file cost time : %s, count:%s", System.currentTimeMillis() - startTime2, count.get()));
    }

    /**
     * 创建文件
     * @param filePath
     * @throws IOException
     */
    private static void writeFIleTxt(String filePath,String value) throws IOException {
//        BufferedWriter bw  = new BufferedWriter(new FileWriter(filePath));//覆盖写入
        BufferedWriter bw  = new BufferedWriter(new FileWriter(filePath,true));//文件追加写入
        //log.info("写入文件：{} ",value);
        if (null != value) {
            bw.write(value);
            bw.newLine();
            bw.flush();
        }
        bw.close();
    }


    /**
     * 下载
     * @param fileUrl
     * @return
     */
    public static void downloadVideoKs(Integer lineNum,String fileUrl) {
        long l = 0L;
        String savePath = null;
        String staticAndMksDir = null;
        if (fileUrl != null) {
            try {
                //下载时文件名称
                String fileName = ".mp4";
                if(fileUrl.contains(".mp4") || fileUrl.contains(".MP4")){
                    fileName = fileUrl.substring(fileUrl.lastIndexOf("."));
                }
                //String dataStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
                String uuidName = UUID.randomUUID().toString().replace("-","");
                //path = "resources/images/"+dataStr+"/"+uuidName+fileName;
                //savePath = "/Volumes/ws/ks_res/"+uuidName+fileName;
                savePath = "D:\\temp\\ks_res\\"+uuidName+fileName;

                String resultTxt = String.format(lineNum+"-保存视频路径 : %s", savePath);
                System.out.println(resultTxt);
                String outPutFilePath = "D:\\temp\\test2.txt";
                //writeFIleTxt(outPutFilePath,resultTxt);

                //staticAndMksDir = Paths.get(ResourceUtils.getURL("classpath:").getPath(),"resources", "images",dataStr).toString();
                HttpUtil.downloadFile(fileUrl, savePath);
            } catch (Exception e) {
                //log.error("下载失败：{}",fileUrl);
                e.printStackTrace();
            } finally {

            }
        }
    }
}
