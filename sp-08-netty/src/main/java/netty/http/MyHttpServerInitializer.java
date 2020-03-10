package netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;

/**
 * @author qixuan.chen
 * @date 2020-02-12 20:05
 */
public class MyHttpServerInitializer extends ChannelInitializer<SocketChannel> {

//    private final boolean isClient;
//
//    public MyHttpServerInitializer (boolean isClient) {
//        this.isClient = isClient;
//    }

    /**
     *  重写父类其中的方法（快捷键 ctrl+o）
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());//http编码解码器
        pipeline.addLast(new HttpObjectAggregator(2048));/*HTTP 消息的合并处理*/
        pipeline.addLast("MyHttpServerHandler",new MyHttpServerHandler());


//        if(isClient){
//            pipeline.addLast("decoder", new HttpResponseDecoder());
//            pipeline.addLast("encoder", new HttpResponseEncoder());
//        } else{
//            pipeline.addLast("decoder", new HttpRequestDecoder());
//            pipeline.addLast("encoder", new HttpRequestEncoder());
//            System.out.println("add HttpRequestDecoder");
//        }
    }

}

