package nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author qixuan.chen
 * @createDate 2019-11-30 17:21
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/main/1.txt");
        FileChannel channel01 = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/2.txt");
        FileChannel channel02 = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true){
            // 在这里要对Buffer进行clear【清空Buffer，重置属性】
            byteBuffer.clear();
            int read = channel01.read(byteBuffer);
            if(read==-1){
                // 读取结束
                break;
            }
            // 翻转后再写入
            byteBuffer.flip();
            channel02.write(byteBuffer);
        }
        channel01.close();
        channel02.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
