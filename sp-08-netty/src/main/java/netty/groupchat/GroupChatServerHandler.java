package netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qixuan.chen
 * @date 2020-03-09 13:05
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 私聊功能，定义一个HashMap来存储
     */
    private static Map<String, Channel> channels = new HashMap();


    /**
     * 定义一个Channel组，管理所有的Channel
     * GlobalEventExecutor.INSTANCE是全局的事件执行器，是一个单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * handlerAdded表示连接建立，一旦连接，第一个被执行的方法
     * 在这里把Channel加入到ChannelGroup
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 将该客户端的信息推送给其他在线的客户端,channelgroup的writeAndFlush会遍历所有channel并发送消息
        channelGroup.writeAndFlush(sdf.format(new Date()) + "【客户端】" + channel.remoteAddress() + "加入聊天\n");
        channelGroup.add(channel);
        // 具体使用的时候，key是变化的，比如数据库中存储的userId，通过id就可以找到对应的用户的Channel实现私聊
        channels.put("id100",channel);
    }

    /**
     * 断开连接，将XXX客户离开信息推送给当前在线的客户
     * handlerRemoved每执行一次，channelGroup中的channel也会被移除，不需要手动调用remove方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(sdf.format(new Date()) + "【客户端】" + channel.remoteAddress() + "离开了\n");
        System.out.println("当前ChannelGroup Size：" + channelGroup.size());
    }

    /**
     * channelActive表示channel处于活动状态，提示XXX上线了
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了");
    }

    /**
     * channelInactive表示channel处于不活动状态，提示XXX下线了
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线了");
    }

    /**
     * 读取数据并转发给在线客户
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 获取当前channel
        Channel channel = ctx.channel();
        // 遍历channelGroup，根据不同的情况，回送不同的消息
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                // 非当前channel
                ch.writeAndFlush(sdf.format(new Date()) + "【客户】" + channel.remoteAddress() + "发送消息：" + msg + "\n");
            } else {
                // 自己发送的消息
                ch.writeAndFlush(sdf.format(new Date()) + "【我】发送消息：" + msg + "\n");
            }
        });
    }

    /**
     * 发生异常事件
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭通道
        ctx.close();
    }
}
