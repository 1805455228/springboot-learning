package com.hins.sp21websocket.ws.interceptor;

import com.hins.sp21websocket.ws.util.WsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * Description:
 *
 * @author mpg
 * @date 2021/06/25
 */
@Slf4j
public class WsOutboundInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        MessageHeaders headers = message.getHeaders();
        String simpDestination = headers.get("simpDestination", String.class);
        String simpSessionId = headers.get("simpSessionId", String.class);
        String payload = WsUtil.getPayload(message);

        // TODO: 2021/6/28 config switch
        log.info("WebSocket sessionId:{}, destination:{}, payload:{}", simpSessionId, simpDestination, payload);

        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel messageChannel, boolean b) {

    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel messageChannel, boolean b, Exception e) {

    }

    @Override
    public boolean preReceive(MessageChannel messageChannel) {
        return false;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel messageChannel) {
        return null;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel messageChannel, Exception e) {

    }
}
