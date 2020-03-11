package netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author qixuan.chen
 * @date 2020-03-11 11:50
 */
public class MyHeartBeatServer {

    public static void main(String[] args) {
        //两个线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup wokerGroup = new NioEventLoopGroup(8);
        try {
            // serverBootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup,wokerGroup)
                    .channel(NioServerSocketChannel.class)
                    // 在bossGroup添加一个日志处理器
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 加入一个netty提供的IdleStateHandler
                            /**
                             * 说明：
                             * 1. IdleStateHandler是netty提供的处理空闲状态的处理器
                             * 2. 前三个参数分别是：
                             *      readerIdleTime【读空闲时间】,
                             *      writerIdleTime【写空闲时间】,
                             *      allIdleTime【读写空闲时间】,
                             *    最后一个参数是时间单位
                             * 3. 如果符合上述条件，就会发送一个心跳检测包检测是否还是连接的状态
                             * 4. 文档说明:
                             *    Triggers an {@link IdleStateEvent} when a {@link Channel} has not performed
                             *    read, write, or both operation for a while.
                             *    读空闲、写空闲、或者读写空闲的时候会触发一个IdleStateEvent事件
                             * 5. 当IdleStateEvent触发，就会传递给管道的下一个Handler去处理
                             *    通过调用（触发）下一个handler的userEventTiggered，在该方法中处理IdleStateEvent（可能是读空闲、写空闲、读写空闲事件）
                             */
                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            // 加入一个对空闲检测进一步处理的自定义Handler
                            pipeline.addLast(new MyHeartBeatServerHandler());
                        }
                    });
            // 启动服务器
            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }
    }

}
