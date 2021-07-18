package com.hins.sp20websocket.netty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author qixuan.chen
 * @date 2020-03-14 20:31
 */
@Component
@Slf4j
public class NettyInit {

    @Bean
    public void chatInit() {
        WebsocketServer websocketServer = new WebsocketServer();
        log.info("[netty websocket]启动开始");
        try {
            websocketServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("[netty websocket]启动完成");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                websocketServer.close();
                log.info("[netty websocket]>>>>>jvm shutdown");
                System.exit(0);
            }
        });
    }
}
