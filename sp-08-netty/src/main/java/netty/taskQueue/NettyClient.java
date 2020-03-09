package netty.taskQueue;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author qixuan.chen
 * @date 2020-03-09 13:05
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        // 客户端需要一个事件循环组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            // 创建客户端启动对象
            // 注意客户端使用的是BootStrap，不是ServerBootStrap
            Bootstrap bootstrap = new Bootstrap();

            // 设置相关参数
            bootstrap.group(eventExecutors)// 设置线程组
                    .channel(NioSocketChannel.class)// 设置客户端实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());// 加入自己的处理器
                        }
                    });

            System.out.println("客户端 is ok......");
            // 启动客户端去连接服务器端
            // 关于ChannelFuture，设计到hetty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6688).sync();
            // 给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }


    }
}
