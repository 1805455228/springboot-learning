package netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author qixuan.chen
 * @date 2019-11-24 11:57
 */
@Slf4j
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    /**
     * 实现父类的抽象方法 （快捷键 alt+enter） 服务端接收消息,处理后，并做出回复
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {

        //将消息----读取到----字节数组
        byte[] buffer = new byte[msg.readableBytes()];//根据可读字节数定义一个byte数组
        msg.readBytes(buffer);//将数据读取到字节数组中

        //将字节数组------转------字符串
        String message = new String(buffer, Charset.forName("utf-8"));
        log.info("服务端接收到的消息：{}",message);
        int num = ++this.count;
        log.info("服务端接收到的消息数量：{}",num);


        //向客户端回复消息
        String content = UUID.randomUUID().toString() + "===";
        ByteBuf responseMsg = Unpooled.copiedBuffer(content,Charset.forName("utf-8"));
        channelHandlerContext.writeAndFlush(responseMsg);

    }

    /**
     * 重写父类其中的方法（快捷键 ctrl+o） 异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();//打印异常
        ctx.close();//关闭通道
    }
}
