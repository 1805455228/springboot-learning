package nio.easytest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author qixuan.chen
 * @createDate 2019-12-01 10:02
 */
public class NIOClient {

    public static void main(String[] args) throws IOException {
        // 得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        // 设置非阻塞模式
        socketChannel.configureBlocking(false);
        // 提供服务器端的ip和端口信息
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        // 连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            // 连接失败
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作...");
            }
        }
        // 连接成功，发送数据
        String s = "hello,tomxwd~";
        // 包裹一个字节数组到Buffer中
        ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
        // 发送数据，将buffer数据写入channel
        socketChannel.write(buffer);
        System.in.read();
    }

}
