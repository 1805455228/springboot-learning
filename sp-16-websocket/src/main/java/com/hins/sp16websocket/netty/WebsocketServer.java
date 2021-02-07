package com.hins.sp16websocket.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qixuan.chen
 * @date 2020-03-14 14:16
 */
@Slf4j
public class WebsocketServer{

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;

    private int port = 9092;

    private WebsocketServerInitializer websocketServerInitializer = null;
    private ChannelFuture serverChannelFuture;


    /**
     * 开启
     * @throws Exception
     */
    public void start() throws Exception {
        build();
    }

    /**
     * boss辅助客户端TCP连接请求 worker负责与客户端之前的读写操作
     * 配置客户端的channel类型
     * 配置TCP参数，握手字符串长度设置
     * TCP_NODELAY算法，尽可能发送大块数据，减少充斥的小块数据
     * 开启心跳包活机制，就是客户端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动
     * 配置固定长度接收缓存区分配器
     * 绑定I/O事件的处理类，WebSocketChildChannelHandler中定义
     *
     */
    public void build() {
        long start = System.currentTimeMillis();
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        websocketServerInitializer = new WebsocketServerInitializer();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // 配置TCP参数，握手字符串长度设置
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // TCP_NODELAY算法，尽可能发送大块数据，减少充斥的小块数据
                    .option(ChannelOption.TCP_NODELAY, true)
                    // 开启心跳包活机制，就是客户端、服务端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 配置固定长度接收缓存区分配器
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))
                    // 绑定I/O事件的处理类,websocketServerInitializer中定义
                    .childHandler(websocketServerInitializer);
            long end = System.currentTimeMillis();
            log.info("Netty WebSocket 服务器启动完成，耗时" + (end - start) + "ms，已绑定端口" + port + "阻塞式等候客户端连接");
            serverChannelFuture = serverBootstrap.bind(port).sync();
        } catch (Exception e) {
            log.error("WebSocketHandler build error!" + e.getMessage(), e);
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 关闭 Netty WebSocket服务器，主要是释放连接
     * 连接包括：服务器连接serverChannel、
     * 客户端TCP处理连接bossGroup
     * 客户端I/O操作连接workerGroup
     *
     * 若只使用
     *    bossGroupFuture = bossGroup.shutdownGraceFully()
     *    workerGroupFuture = workerGroup.shutdownGraceFully()
     *    会造成内存泄露
     */
    public void close() {
        serverChannelFuture.channel().close();
        Future<?> bossGroupFuture = bossGroup.shutdownGracefully();
        Future<?> workerGroupFuture = workerGroup.shutdownGracefully();
        try {
            bossGroupFuture.await();
            workerGroupFuture.await();
        } catch (InterruptedException e) {
            log.error("WebSocketHandler close error!" + e.getMessage(), e);
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public WebsocketServerInitializer getWebsocketServerInitializer() {
        return websocketServerInitializer;
    }

    public void setWebsocketServerInitializer(WebsocketServerInitializer websocketServerInitializer) {
        this.websocketServerInitializer = websocketServerInitializer;
    }
}
