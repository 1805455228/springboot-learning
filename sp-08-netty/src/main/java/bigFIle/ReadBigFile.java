package bigFIle;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;


public class ReadBigFile {

    public static void main(String[] args) throws Exception {
        String filePath = "D:\\test.txt";
//        writeFIleTxt(filePath);

        readFileTxt(filePath);

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
            public void handle(String value) {
                count.incrementAndGet();
                //TODO 业务处理
                try {
                    double d = Math.random();
                    // 通过d获取一个[0, 100)之间的整数
                    int time = (int)(d*10);
                    System.out.println(Thread.currentThread()+"=======read========"+count+"=====time====="+time+"===v==="+value);
                    Thread.sleep(time*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                if (!set.contains(value)) {
//                    System.out.println("ConcurrentReadFile read file exception:" + value);
//                }
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
    private static void writeFIleTxt(String filePath) throws IOException {
        //long value = 1000_000_000_000_00L;
        long value = 1000_00L;
        BufferedWriter bw  = new BufferedWriter(new FileWriter(filePath));
        for (int i = 0; i < 500_00; i++) {
            bw.write(String.valueOf(value+"mp4aaaaaaa"));
            bw.newLine();
            value++;
        }
        bw.close();
    }

}
