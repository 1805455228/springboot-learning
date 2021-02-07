package nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author qixuan.chen
 * @createDate 2019-11-30 17:01
 */
public class NIOFileChannel02 {

    public static void main(String[] args) throws IOException {
        File file = new File("g:\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());
        channel.read(byteBuffer);
        // 将字节数据转换为字符串
        System.out.println(new String(byteBuffer.array()));
        channel.close();
        fileInputStream.close();
    }

}
