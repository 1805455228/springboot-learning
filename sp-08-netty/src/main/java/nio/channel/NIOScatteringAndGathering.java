package nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author qixuan.chen
 * @createDate 2019-11-30 20:07
 */
public class NIOScatteringAndGathering {
    public static void main(String[] args) throws IOException {
        // 使用ServerSocketChannel和SocketChannel
        ServerSocketChannel open = ServerSocketChannel.open();
        // 创建端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        // 绑定端口到Socket并启动
        open.socket().bind(inetSocketAddress);
        // 创建Buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        // 等待客户端连接(telnet)
        SocketChannel socketChannel = open.accept();
        // 最多接收8个字节
        int messageLength = 8;
        // 循环读取
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long r = socketChannel.read(byteBuffers);
                // 累计读取的字节数
                byteRead += r;
                System.out.println("byteRead = " + byteRead);
                // 使用流打印，看看当前的buffer的position和limit
                Arrays.asList(byteBuffers).stream().map(byteBuffer ->
                        "position = " + byteBuffer.position() + ",limit = " + byteBuffer.limit()
                ).forEach(System.out::println);
            }
            // 将所有的buffer进行翻转flip
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());
            // 将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                long w = socketChannel.write(byteBuffers);
                byteWrite += w;
            }
            // 将所有buffer进行clear复位
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());
            System.out.println("byteRead = " + byteRead + ", byteWrite = " + byteWrite + ",messageLength = " + messageLength);
        }
    }
}
