package netty.protocoltcp;

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
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    /**
     * 实现父类的抽象方法 （快捷键 alt+enter） 服务端接收消息,处理后，并做出回复
     * @param channelHandlerContext
     * @param messageProtocol
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {

        log.info("=============服务端=====接收消息========================");
        int len = messageProtocol.getLen();
        byte[] content = messageProtocol.getContent();
        String message = new String(content,Charset.forName("utf-8"));
        log.info("服务端接收到的数据包长度：{}",len);
        log.info("服务端接收到的数据包内容：{}",message);
        int num = ++this.count;
        log.info("服务端接收到的数据包数量：{}",num);


        //先构建协议数据包，再向客户端回复消息
        log.info("=============服务端=====向====客户端回复消息===================");
        String responseMsg = UUID.randomUUID().toString() + "===";
        int responseLen = responseMsg.getBytes("utf-8").length;
        byte[] responseContent = responseMsg.getBytes("utf-8");

        //将消息封装成-----数据包对象----再发送(回复)
        MessageProtocol msg = new MessageProtocol();
        msg.setLen(responseLen);
        msg.setContent(responseContent);

        channelHandlerContext.writeAndFlush(msg);//回复发送

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
