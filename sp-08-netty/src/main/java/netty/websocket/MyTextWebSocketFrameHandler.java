package netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

 /**
  * 这里用的泛型TextWebSocketFrame类型，表示一个文本帧（frame）
 * @author qixuan.chen
 * @date 2020-03-11 11:50
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * Web客户端连接后，第一个会触发这个方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // id表示唯一的值，LongText是唯一的,ShortText有可能重复
        System.out.println("MyTextWebSocketFrameHandler.handlerAdded被调用 " + ctx.channel().id().asLongText());
        System.out.println("MyTextWebSocketFrameHandler.handlerAdded被调用 " + ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyTextWebSocketFrameHandler.handlerRemoved被调用" + ctx.channel().id().asLongText());
        System.out.println("MyTextWebSocketFrameHandler.handlerRemoved被调用" + ctx.channel().id().asShortText());
    }

    /**
     * 发生异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("MyTextWebSocketFrameHandler.exceptionCaught被调用---异常发生 " + cause.getMessage());
        // 关闭连接
        ctx.close();
    }

    /**
     * 处理客户端数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器端收到消息:" + msg.text());
        // 回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间" + LocalDateTime.now() + " " + msg.text()));
    }
}
