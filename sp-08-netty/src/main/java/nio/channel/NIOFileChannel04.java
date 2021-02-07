package nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author qixuan.chen
 * @createDate 2019-11-30 17:39
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws IOException {
        File file1 = new File("g:\\file01.txt");
        File file2 = new File("g:\\file02.txt");
        FileInputStream fileInputStream = new FileInputStream(file1);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        FileChannel source = fileInputStream.getChannel();
        FileChannel dest = fileOutputStream.getChannel();
        // 使用transferFrom完成拷贝
        dest.transferFrom(source,0,source.size());
        source.close();
        dest.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
