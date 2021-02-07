package nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author qixuan.chen
 * @createDate 2019-12-01 19:54
 */
public class GroupChatClient {

    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    /**
     * 无参构造器
     */
    public GroupChatClient() throws IOException {
        selector = Selector.open();
        // 连接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        // 将Channel注册到Selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 得到username
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " 客户端准备完毕");
    }

    /**
     * 向服务器发送消息
     * @param info
     */
    public void sendInfo(String info) {
        info = username + " say: " + info;
        try{
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 读取从服务器回复的信息
     */
    public void readInfo(){
        try{
            int readChannels = selector.select();
            if(readChannels>0){
                // 有事件发生的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if(key.isReadable()){
                        // 得到相关的通道
                        SocketChannel sc = ((SocketChannel) key.channel());
                        // 得到一个Buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        // 读取数据
                        sc.read(buffer);
                        // 把读取到的数据转成字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                    // 删除当前SelectionKey，防止重复操作
                    iterator.remove();
                }
            } else {
                //System.out.println("没有可用的通道");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // 启动客户端
        GroupChatClient chatClient = new GroupChatClient();
        // 启动一个线程，每隔三秒，读取从服务器端发送的数据
        new Thread(()->{
            while (true){
                chatClient.readInfo();
                try{
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
        // 客户端发送数据给服务器端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }
    }

}
