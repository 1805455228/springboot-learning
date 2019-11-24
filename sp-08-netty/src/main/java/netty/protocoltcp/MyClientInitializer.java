package netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author qixuan.chen
 * @date 2019-11-24 11:44
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline channelPipeline = socketChannel.pipeline();

        channelPipeline.addLast(new MyMessageDecoder());//解码（接收回复时）
        channelPipeline.addLast(new MyMessageEncoder());//编码 （发送消息时）
        channelPipeline.addLast(new MyClientHandler());
    }
}
