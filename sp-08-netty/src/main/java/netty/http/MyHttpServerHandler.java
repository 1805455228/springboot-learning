package netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

/**
 * @author qixuan.chen
 * @date 2020-02-12 20:11
 */
@Slf4j
public class MyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     *  重写父类其中的方法（快捷键 ctrl+o） 客户端发送消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest){

            log.info("MyHttpServerHandler---hash："+this.hashCode());
            log.info("pipeline--hash："+ctx.pipeline().hashCode());
//            Http无状态，每次传输完成即断开
//            每次浏览器刷新，都是新的Handler、Channel、pipeline

            // 根据uri进行过滤
            HttpRequest request = (HttpRequest) msg;
            URI uri = new URI(request.uri());
            if ("/favicon.ico".equals(uri.getPath())){
                System.out.println("浏览器请求了favicon，不做响应");
                return;
            }

            log.info("msg类型："+msg.getClass());
            log.info("客户端地址："+ctx.channel().remoteAddress());

            // 返回信息
            ByteBuf content = Unpooled.copiedBuffer("Hello,World 我是服务端", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());//长度
            ctx.writeAndFlush(response);
        }
    }
}
