package netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author qixuan.chen
 * @date 2019-11-24 16:47
 */
@Slf4j
public class MyMessageDecoder extends ReplayingDecoder<Void> {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        log.info("MyMessageEncode 消息解码=====被调用");
        //需要将得到的二进制字节码-----转------协议数据包（对象）
        int len = byteBuf.readInt();

        byte[] content = new byte[len];
        byteBuf.readBytes(content);

        //封装成数据包对象-----放进list-----传递下一个handler业务处理
        MessageProtocol message = new MessageProtocol();
        message.setLen(len);
        message.setContent(content);

        list.add(message);

    }
}
