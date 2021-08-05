package com.hins.sp21websocket.ws.util;


import com.hins.sp21websocket.ws.entity.WsUserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.nio.charset.StandardCharsets;
import java.security.Principal;

/**
 * @author mpg
 * @date 2021/6/24
 */
@Slf4j
public class WsUtil {

    /**
     * 获得登陆session
     *
     * @param accessor
     * @return
     */
    public static WsUserSession getSession(StompHeaderAccessor accessor) {
        //不是首次连接，已经成功登陆
        Principal principal = accessor.getUser();
        if (!(principal instanceof WsUserSession)) {
            String sessionId = accessor.getSessionId();
            log.warn("WebSocket error principal, sessionId:{}", sessionId);
            return null;
        }
        return (WsUserSession) principal;
    }

    /**
     * 获得登陆session
     *
     * @param principal
     * @return
     */
    public static WsUserSession getSession(Principal principal) {
        //不是首次连接，已经成功登陆
        if (!(principal instanceof WsUserSession)) {
            log.warn("WebSocket error principal");
            return null;
        }
        return (WsUserSession) principal;
    }


    /**
     * 获得传输的消息内容
     *
     * @param message
     * @return
     */
    public static String getPayload(Message<?> message) {
        Object msgPayload = message.getPayload();
        String payload = null;
        if (msgPayload instanceof byte[]) {
            payload = new String((byte[]) msgPayload, StandardCharsets.UTF_8);
        }
        return payload;
    }
}
