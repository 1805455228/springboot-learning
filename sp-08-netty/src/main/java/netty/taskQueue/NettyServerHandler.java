package netty.taskQueue;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * 说明：
 *  1. 我们自定义一个Handler 需要继承netty规定好的某个HandlerAdapter适配器
 *  2. 这时我们自定义的Handler才能称之为一个Handler
 *  SimpleChannelInboundHandler 与 ChannelInboundHandlerAdapter 区别
 * @author qixuan.chen
 * @date 2020-03-09 13:01
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据事件（这里我们可以读取客户端发送的数据）
     *
     * @param ctx ChannelHandlerContext：上下文对象，含有管道pipeline，通道channel，地址
     * @param msg Object：就是客户端发送的数据，默认是Object类型
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("read...");
        //串行执行----从上到下
//        Thread.sleep(10*1000);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,服务端10秒了",CharsetUtil.UTF_8));

        // 这里有一个非常耗费时间的业务   ->  异步执行    -> 提交到该Channel对应的NioEventLoop的taskQueue中即可

        // 解决方案1：用户程序自定义的普通任务

            ctx.channel().eventLoop().execute(()->{
                try {
                    Thread.sleep(10*1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,服务端10秒了",CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            ctx.channel().eventLoop().execute(()->{
                try {
                    Thread.sleep(20*1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,服务端30秒了",CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });


        // 用户自定义定时任务    ->  该任务是提交到scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(()->{
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello,服务端5秒了",CharsetUtil.UTF_8));
        },5, TimeUnit.SECONDS);

        System.out.println("go on...");

        /*
            System.out.println("服务器读取线程 ："+Thread.currentThread().getName());
            System.out.println("读取事件发生=================");
            System.out.println("server ctx = " + ctx);
            System.out.println("看看Channel和pipeline的关系");
            Channel channel = ctx.channel();
            ChannelPipeline pipeline = ctx.pipeline();// 本质是一个双向链表，设计到出栈入栈的问题
            // msg转为一个ByteBuffer
            // ByteBuf是netty提供的，不是NIO的ByteBuffer，Netty提供的性能更高
            ByteBuf buf = (ByteBuf) msg;
            System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
            System.out.println("客户端的地址是：" + ctx.channel().remoteAddress());
        */

    }

    /**
     * 数据读取完毕，触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // write+flush 将数据写入到缓存，并刷新
        // 一般来讲，我们对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("--hello,客户端",CharsetUtil.UTF_8));
    }

    /**
     * 处理异常，一般是需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
