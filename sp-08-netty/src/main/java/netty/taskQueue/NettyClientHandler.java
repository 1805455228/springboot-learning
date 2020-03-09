package netty.taskQueue;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author qixuan.chen
 * @date 2020-03-09 13:06
 * extends SimpleChannelInboundHandler<ByteBuf> 与 ChannelInboundHandlerAdapter 区别
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {


    /**
     * 当通道就绪的时候就触发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client :" + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server！！！", CharsetUtil.UTF_8));
    }

    /**
     * 当通道有读取事件的时候，会触发该方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复的消息：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址："+ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("客户端异常发生");
        cause.printStackTrace();
        ctx.close();
    }
}
