package netty.protocoltcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author qixuan.chen
 * @date 2019-11-24 11:15
 */
public class MyClient {

    /**
     * 客户端
     * MyClient------MyClientInitializer-------MyClientHandler
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .handler(new MyClientInitializer());//自定义一个初始化类

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",7000).sync();
        channelFuture.channel().closeFuture().sync();

    }
}
