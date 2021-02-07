package nio.channel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author qixuan.chen
 * @createDate 2019-11-30 16:43
 */
public class NIOFileChannel01 {

    public static void main(String[] args) throws IOException {
        String str = "hello,tomxwd";
        // 创建一个输出流  -> Channel
        FileOutputStream fileOutputStream = new FileOutputStream("g:\\file01.txt");
        // 通过fileOutputStream 获取对应的FileChannel(真实类型是FileChannelImpl)
        FileChannel fileChannel = fileOutputStream.getChannel();
        // 创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 将str放入到ByteBuffer
        byteBuffer.put(str.getBytes());
        // 对ByteBuffer进行翻转
        byteBuffer.flip();
        // 写入到FileChannel中
        fileChannel.write(byteBuffer);
        // 关闭流
        fileChannel.close();
        fileOutputStream.close();
    }

}
