package nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 *
 * @author qixuan.chen
 * @createDate 2019-12-02 20:39
 */
public class NewIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7001));
        String fileName = "Xxx.zip";
        // 得到一个文件的Channel
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();
        // 准备发送
        long startTime = System.currentTimeMillis();
        // 在Linux下一个transferTo方法就可以完成传输
        // 在windows下一次调用transferTo只能发送8m，就需要分段传输文件，而且要注意传输时的位置
        // transferTo底层就使用了零拷贝
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的总的字节数："+transferCount+"耗时："+(System.currentTimeMillis()-startTime));
        fileChannel.close();
        socketChannel.close();
    }

}
