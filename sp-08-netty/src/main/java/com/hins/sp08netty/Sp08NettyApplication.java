package com.hins.sp08netty;

import com.hins.sp08netty.config.NettyConfig;
import com.hins.sp08netty.websocket.ServerBootStrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.net.InetSocketAddress;

@SpringBootApplication
public class Sp08NettyApplication  implements CommandLineRunner{
    private static final Logger logger = LoggerFactory.getLogger(Sp08NettyApplication.class);
    @Autowired
    private ServerBootStrap ws;

    public static void main(String[] args) {
        SpringApplication.run(Sp08NettyApplication.class, args);
    }


    // 注意这里的 run 方法是重载自 CommandLineRunner
    @Override
    public void run(String... args) throws Exception {

        logger.info("Netty's ws server is listen: " + NettyConfig.WS_PORT);
        InetSocketAddress address = new InetSocketAddress(NettyConfig.WS_HOST, NettyConfig.WS_PORT);
        ChannelFuture future = ws.start(address);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                ws.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();

    }

}
