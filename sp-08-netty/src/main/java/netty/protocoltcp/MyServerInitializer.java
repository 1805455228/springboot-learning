package netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author qixuan.chen
 * @date 2019-11-24 15:44
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast(new MyMessageDecoder());//加入解码器 (接收消息)
        channelPipeline.addLast(new MyMessageEncoder());//加入解码器（回复消息）
        channelPipeline.addLast(new MyServerHandler());
    }
}
