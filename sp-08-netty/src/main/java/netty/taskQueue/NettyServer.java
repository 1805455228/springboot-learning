package netty.taskQueue;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * @author qixuan.chen
 * @date 2020-03-09 13:00
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        /*
            说明：
            1. 创建两个线程组BossGroup以及WorkGroup
            2. BossGroup只是处理连接请求，真正与客户端的业务处理，会交给WorkerGroup完成
            3. 两个都是无限循环
            4. BossGroup和WorkerGroup含有的子线程（NioEventLoop）的个数，默认是CPU的核数*2
         */
        // 创建BossGroup 以及 WorkGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 使用链式编程的方式进行设置
            bootstrap.group(bossGroup, workerGroup)          // 设置两个线程组
                    .channel(NioServerSocketChannel.class)  // 使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列等待连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)// 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道初始化对象（匿名对象）
                        // 向workerGroup关联的pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("客户端SocketChannel的hashCode：" + ch.hashCode());// 可以使用一个集合管理所有的Channel，在推送消息的时候，可以将业务加入到各个Channel对应的NioEventLoop的taskQueue或者scheduleTaskQueue
                            ch.pipeline().addLast(new NettyServerHandler());//添加处理器到pipeline尾部
                        }
                    });//给workerGroup的EventLoop对应的管道设置处理器
            System.out.println("==============服务器 is ready==============");

            // 绑定一个端口并同步，生成了一个ChannelFuture对象
            // 启动了服务器并绑定端口
            ChannelFuture cf = bootstrap.bind(6688).sync();

            // 给cf注册监听器，监控我们关心的事件
            cf.addListener(future -> {
                if(cf.isSuccess()){
                    System.out.println("监听端口6688成功！");
                }else{
                    System.out.println("监听端口6688失败！");
                }
            });

            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
