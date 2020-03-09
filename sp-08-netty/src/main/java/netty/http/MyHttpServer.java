package netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author qixuan.chen
 * @date 2020-02-12 19:56
 */
public class MyHttpServer {



        public static void main(String[] args) {

            NioEventLoopGroup bossGroup = new NioEventLoopGroup();
            NioEventLoopGroup workGroup = new NioEventLoopGroup();//默认数量是：cpu核数乘以2

            try {
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                serverBootstrap.group(bossGroup, workGroup).
                        channel(NioServerSocketChannel.class).
                        childHandler(new MyHttpServerInitializer());
                ChannelFuture channelFuture = serverBootstrap.bind(8086).sync();
                // 监听关闭事件
                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
            }
        }



}
