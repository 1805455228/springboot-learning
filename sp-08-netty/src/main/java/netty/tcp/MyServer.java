package netty.tcp;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务端
 * @author qixuan.chen
 * @date 2019-11-24 11:17
 */
public class MyServer {

    /**
     * 服务端
     * @param args
     */
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {


            ServerBootstrap serverBootStrap = new ServerBootstrap();
            //双线程（一个负责接收，一个负责处理）
            serverBootStrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());//自定义一个初始化类

            ChannelFuture channelFuture = serverBootStrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
