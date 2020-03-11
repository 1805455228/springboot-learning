package netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author qixuan.chen
 * @date 2020-03-11 11:50
 */
public class MyWebSocketServer {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(8);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            /**
                             *  1. 因为基于Http协议，所以要使用Http的编解码器
                             *  2. 而且是以块方式写的，所以添加ChunkedWrite处理器
                             *  3. 因为Http数据在传输过程中是分段的，所以用HttpObjectAggregator，
                             *     把多个段聚合，这就是为什么当浏览器发送大量数据的时候，就会发出多次Http请求
                             *  4. 对于WebSocket，它的数据是以帧（frame）的形式传递的，可以看到
                             *     WebSocketFrame下面有六个子类
                             *  5. 浏览器请求的时候ws://localhost:7000/XXX（表示请求的uri）
                             *  6. WebSocketServerProtocolHandler核心功能是将Http协议升级为ws协议，保持长连接
                             *  7. 最后添加一个自定义handler，用于处理具体业务逻辑
                             *  8. 是通过一个状态码101
                             */
                            pipeline.addLast(new HttpServerCodec())
                                    .addLast(new ChunkedWriteHandler())
                                    .addLast(new HttpObjectAggregator(8192))
                                    .addLast(new WebSocketServerProtocolHandler("/hello"))
                                    .addLast(new MyTextWebSocketFrameHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
