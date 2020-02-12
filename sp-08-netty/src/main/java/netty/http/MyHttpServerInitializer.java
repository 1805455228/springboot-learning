package netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;

/**
 * @author qixuan.chen
 * @date 2020-02-12 20:05
 */
public class MyHttpServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     *  重写父类其中的方法（快捷键 ctrl+o）
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());//http编码解码器
        pipeline.addLast("MyHttpServerHandler",new MyHttpServerHandler());
    }

}

