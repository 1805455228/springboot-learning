package netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 群聊系统服务端
 *
 * @author qixuan.chen
 * @date 2020-03-09 13:05
 */
public class GroupChatServer {

    /**
     * 监听端口
     */
    private int port;

    public GroupChatServer(int port) {
        this.port = port;
    }

    /**
     * 编写run方法，处理客户端的请求
     */
    public void run() throws InterruptedException {
        // 创建两个线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(8);
        try {
            // 启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 设置参数
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 获取Pipeline
                            // 向Pipeline加入一个解码器以及一个编码器,以及自己的业务处理Handler
                            ch.pipeline()
                                    .addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast(new GroupChatServerHandler());
                        }
                    });
            System.out.println("netty群聊服务器启动");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            // 监听关闭事件
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new GroupChatServer(7000).run();
    }

}
