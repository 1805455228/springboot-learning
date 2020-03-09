package netty.simple;

/**
 * @author qixuan.chen
 * @date 2020-03-09 12:10
 */
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.TimeUnit;


/**
 * @author qixuan.chen
 * @date 2020-03-09 12:08
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     *
     * @param ctx s上下文对象
     * @param msg 客户端消息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+","+msg);
        ctx.channel().writeAndFlush("From Server:"+ ctx.channel().remoteAddress());

        //用户自定义普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                //...
            }
        });

        //用户自定义定时任务
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {

            }
        }, 60, TimeUnit.SECONDS);

    }

    /**
     * 捕获异常，一般会关闭连接
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
