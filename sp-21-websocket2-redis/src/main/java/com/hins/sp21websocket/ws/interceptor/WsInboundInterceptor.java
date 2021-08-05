package com.hins.sp21websocket.ws.interceptor;


import com.hins.sp21websocket.dto.CenterMemberInfoResp;
import com.hins.sp21websocket.service.CenterMemberService;
import com.hins.sp21websocket.ws.entity.WsConstant;
import com.hins.sp21websocket.ws.entity.WsUserSession;
import com.hins.sp21websocket.ws.util.WsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ExecutorChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author mpg
 * @date 2021/06/25
 */
@Slf4j
public class WsInboundInterceptor implements ExecutorChannelInterceptor {

    @Resource
    private CenterMemberService centerMemberService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        Assert.notNull(accessor, "accessor is null");
        StompCommand command = accessor.getCommand();

        if (StompCommand.CONNECT.equals(command)) {
            //1. 判断是否首次连接请求
            boolean connectSuccess = connectPreSend(accessor);
            return connectSuccess ? message : null;
        }
        WsUserSession wsUserSession = getSessionAndTryLogin(accessor);
        if (null == wsUserSession) {
            return null;
        }
        if (StompCommand.SEND.equals(command)) {
            sendPreSend(accessor, message, wsUserSession);
        } else {
            othersPreSend(accessor, command, wsUserSession);
        }
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


    @Override
    public Message<?> beforeHandle(Message<?> message, MessageChannel channel, MessageHandler handler) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        Assert.notNull(accessor, "accessor is null");
        WsUserSession wsUserSession = WsUtil.getSession(accessor);
        if (null != wsUserSession) {
            // TODO: 2021/6/28 set user: unnecessary
        }
        return message;
    }

    @Override
    public void afterMessageHandled(Message<?> message, MessageChannel channel, MessageHandler handler, Exception ex) {
        // TODO: 2021/6/28 remove user info: unnecessary
    }

    /**
     * 连接前的权限认证
     *
     * @param accessor
     * @return
     */
    private boolean connectPreSend(StompHeaderAccessor accessor) {
        //2. 验证是否登录
        String jwtToken = accessor.getFirstNativeHeader(WsConstant.TOKEN);
        CenterMemberInfoResp.MemberBaseResp memberInfo = centerMemberService.getMemberInfo(jwtToken);
        if (null == memberInfo || null == memberInfo.getMemberId()) {
            log.warn("ws auth valid failed");
            return false;
        }
        WsUserSession wsUserSession = new WsUserSession(memberInfo);
        accessor.setUser(wsUserSession);
        String sessionId = accessor.getSessionId();

        log.info("WebSocket user login, user:{}, sessionId:{}", wsUserSession.getName(), sessionId);
        return true;
    }

    /**
     * 接收前端消息前的权限认证
     *
     * @param accessor
     * @return
     */
    private void sendPreSend(StompHeaderAccessor accessor, Message<?> message, WsUserSession wsUserSession) {
        String username = wsUserSession.getName();
        String payload = WsUtil.getPayload(message);
        String sessionId = accessor.getSessionId();
        String destination = accessor.getDestination();
        log.info("WebSocket user:{}, command:{}, destination:{}, sessionId:{}, payload:{}", username, StompCommand.SEND, destination, sessionId, payload);
    }

    /**
     * 其他类型消息前的权限认证
     *
     * @param accessor
     * @return
     */
    private void othersPreSend(StompHeaderAccessor accessor, StompCommand stompCommand, WsUserSession wsUserSession) {
        String username = wsUserSession.getName();
        String sessionId = accessor.getSessionId();
        log.info("WebSocket user:{}, command:{}, sessionId:{}, destination:{}", username, stompCommand, sessionId, accessor.getDestination());
    }


    /**
     * 获得Session
     * 第一次不成功后，会尝试重新登陆
     *
     * @param accessor
     * @return
     */
    private WsUserSession getSessionAndTryLogin(StompHeaderAccessor accessor) {
        WsUserSession wsUserSession = WsUtil.getSession(accessor);
        if (null != wsUserSession) {
            return wsUserSession;
        }
        log.info("WsInboundInterceptor try login");
        boolean login = this.connectPreSend(accessor);
        if (!login) {
            return null;
        }
        log.info("WsInboundInterceptor try login succeed");
        wsUserSession = WsUtil.getSession(accessor);
        return wsUserSession;
    }

}
