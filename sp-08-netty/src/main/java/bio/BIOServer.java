package bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qixuan.chen
 * @createDate 2019-11-27 23:48
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {
        // 线程池机制
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        // 创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        while (true) {
            // 监听，等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("连接到客户端了");

            // 创建一个线程与之通信
            newCachedThreadPool.execute(() -> handler(socket));
        }

    }

    // 编写一个handler方法，和客户端通讯
    public static void handler(Socket socket){
        try {
            System.out.println("线程信息 id = "+Thread.currentThread().getId()+"名字 = "+Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            // 通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
            // 循环读取客户端发送的数据
            while (true){
                System.out.println("线程信息 id = "+Thread.currentThread().getId()+"名字 = "+Thread.currentThread().getName());
                int read = inputStream.read(bytes);
                if(read!=-1){
                    // 输出客户端发送的数据
                    System.out.println(new String(bytes,0,read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
