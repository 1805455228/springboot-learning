package com.hins.sp20websocket.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author qixuan.chen
 * @date 2020-03-14 17:20
 */
public class WebsocketServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketHandler) {
        //http编解码器
        socketHandler.pipeline().addLast("http-codec", new HttpServerCodec());
//        //HTTP头、体拼成完整的HTTP请求
        socketHandler.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        //方便大文件传输，不过实质都是短的文本数据
        socketHandler.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        socketHandler.pipeline().addLast("http-handler", new HttpRequestHandler());
        socketHandler.pipeline().addLast("websocket-handler", new WebSocketServerHandler());
    }
}
