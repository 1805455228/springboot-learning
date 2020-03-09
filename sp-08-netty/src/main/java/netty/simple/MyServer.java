package netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author qixuan.chen
 * @date 2020-03-09 12:08
 */

public class MyServer {
    public static void main(String[] args) throws Exception {
        /**
         * 定义事件循环组,就是一个死循环，不断接受客户端发来的消息*/
        EventLoopGroup bossGroup = new NioEventLoopGroup();//获取转发链接
        EventLoopGroup workerGroup = new NioEventLoopGroup();//处理连接

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new MyServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

