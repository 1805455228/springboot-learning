package netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author qixuan.chen
 * @date 2020-03-11 11:50
 */
public class MyHeartBeatServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * @param ctx 上下文对象
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 将evt向下转型IdleStateEvent
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
                default:
            }
            System.out.println(ctx.channel().remoteAddress() + "--------超时事件发生--------" + eventType);
            System.out.println("在这里服务器判断空闲类型，做出相应处理");
//             如果发生空闲，直接关闭channel
//            ctx.channel().close();
        }
    }
}
