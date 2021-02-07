package nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author qixuan.chen
 * @createDate 2019-12-01 18:35
 */
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannle;
    private static final int PORT = 6667;

    /**
     * 无参构造器
     */
    public GroupChatServer() {
        try {
            // 获取选择器
            selector = Selector.open();
            // 获取ServerSocketChannel
            listenChannle = ServerSocketChannel.open();
            // 绑定端口
            listenChannle.bind(new InetSocketAddress(PORT));
            // 设置非阻塞模式
            listenChannle.configureBlocking(false);
            // 将listenChannel注册到selector上去,关注的是accept事件
            listenChannle.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 监听
    public void listen() {
        try {
            // 循环处理
            while (true) {
                int count = selector.select(2000);
                // 有事件需要处理
                if (count > 0) {
                    // 遍历得到的SelectionKey集合
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        // 监听到的是ACCPET事件
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = listenChannle.accept();
                            // 设置为非阻塞模式
                            socketChannel.configureBlocking(false);
                            // 将该通道注册到selector中
                            socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                            // 给出提示XXX上线
                            System.out.println(socketChannel.getRemoteAddress() + "上线");
                        }
                        // 监听到的是READ事件
                        if (key.isReadable()) {
                            // 处理读事件的方法
                            readData(key);
                        }
                        // 处理完成移除当前事件，防止重复
                        keyIterator.remove();
                    }
                } else {
                    System.out.println("等待");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 读取客户端消息
     *
     * @param selectionKey
     */
    private void readData(SelectionKey selectionKey) {
        // 定义一个SocketChannel
        SocketChannel channel = null;
        try {
            // 取得关联的Channel
            channel = (SocketChannel) selectionKey.channel();
            // 创建Buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = channel.read(buffer);
            // 根据read的值做处理
            if (read > 0) {
                // 把Buffer转String
                String msg = new String(buffer.array());
                // 输出该消息
                System.out.println("from 客户端：" + msg);
                // 向其他客户端转发消息(排除自己)
                sendInfoToOtherClients(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了...");
                // 取消注册
                selectionKey.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * 转发消息给其他客户（通过通道）
     *
     * @param msg
     * @param self
     */
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中...");
        // 遍历 所有注册到Selector上的SocketChannel并排除self
        for (SelectionKey key : selector.keys()) {
            // 通过key取出对应的SocketChannel
            Channel targetChannel = key.channel();
            // 排除self
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                // 转型为SocketChannel
                SocketChannel dest = (SocketChannel) targetChannel;
                // 将msg存储到buffer中
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                // 将Buffer的数据写入通道
                dest.write(buffer);
            }

        }

    }

    public static void main(String[] args) {
        // 创建服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

}
