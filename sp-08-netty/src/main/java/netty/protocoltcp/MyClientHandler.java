package netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author qixuan.chen
 * @date 2019-11-24 11:57
 */
@Slf4j
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    /**
     * 重写父类其中的方法（快捷键 ctrl+o） 客户端发送消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //向服务端发送---5条消息
        for (int i=0;i<5;i++){
            String msg = "你大爷的,十六进制==="+i;
            byte[] content = msg.getBytes(Charset.forName("utf-8"));
            int len = msg.getBytes(Charset.forName("utf-8")).length;

            //将消息封装成-----数据包对象----再发送
            MessageProtocol message = new MessageProtocol();
            message.setContent(content);
            message.setLen(len);
            ctx.writeAndFlush(message);//发送协议数据包
        }
    }

    /**
     * 实现父类的抽象方法 （快捷键 alt+enter） 客户端接收回复消息
     * @param channelHandlerContext
     * @param messageProtocol
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,  MessageProtocol messageProtocol) throws Exception {

        int len = messageProtocol.getLen();
        byte[] content = messageProtocol.getContent();

        String message = new String(content,Charset.forName("utf-8"));

        log.info("客户端接收到的数据包长度：{}",len);
        log.info("客户端接收到的数据包内容：{}",message);
        int num = ++this.count;
        log.info("客户端接收到的数据包数量：{}",num);

    }

    /**
     * 重写父类其中的方法（快捷键 ctrl+o） 异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("异常信息, {}",cause.getMessage());
        ctx.close();//关闭通道
    }
}
