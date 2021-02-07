package nio.easytest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author qixuan.chen
 * @createDate 2019-11-30 22:22
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {
        // 创建ServerSocketChannel  -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 得到一个Selector对象
        Selector selector = Selector.open();
        // 绑定端口6666，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 把ServerSocketChannel注册到Selector，关心的事件是OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 循环等待客户端连接
        while (true) {
            // 这里等待一秒，如果没有事件发生就返回
            if (selector.select(1000) == 0) {
                // 没有事件发生
                System.out.println("服务器等待了一秒，无连接");
                continue;
            }
            // 如果返回>0,就获取到相关的SelectionKey集合
            // 1. 如果返回的>0，表示已经获取到关注的事件
            // 2. 通过selectedKeys() 返回关注事件的集合
            // 通过selectionKeys反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 遍历Set<SelectionKey>集合，使用迭代器遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                // 获取到SelectionKey
                SelectionKey key = keyIterator.next();
                // 根据key对应的通道发生的事件做相应的处理
                if (key.isAcceptable()) {
                    // 如果是OP_ACCEPT事件，有新的客户端连接
                    // 给该客户端生成SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("客户端连接成功，生成了一个SocketChannel" + socketChannel.hashCode());
                    // 将当前的socketChannel注册到Selector上，关注事件为OP_READ，同时给SocketChannel关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) {
                    // 如果是OP_READ事件
                    // 通过key反向获取到对应的channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 获取到该channel关联的Buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端 : " + new String(buffer.array()));
                    buffer.clear();
                }
                // 手动从集合中删除当前的selectKey，防止重复操作
                keyIterator.remove();
            }

        }
    }

}
