package com.hins.sp21websocket.ws.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.BufferingStompDecoder;
import org.springframework.messaging.simp.stomp.StompDecoder;
import org.springframework.messaging.simp.stomp.StompEncoder;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderInitializer;
import org.springframework.util.Assert;
import org.springframework.web.socket.*;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;
import org.springframework.web.socket.messaging.StompSubProtocolHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * StompSubProtocol装饰器
 * fix "No decoder for session id" Exception if session is closed
 * 代理原始StompSubProtocolHandler
 * 主要用于捕获No decoder异常，若session已经关闭，则正常返回即可
 * 实现逻辑参考
 * <a href="https://github.com/spring-projects/spring-framework/commit/f425a993e7be82ffdbdda24370925a34c42925f2">Ignore missing STOMP decoder if session closed<a/>
 *
 * @author mpg
 * @date 2021/06/25
 */
@Slf4j
public class StompSubProtocolHandlerDecorator extends StompSubProtocolHandler {

    private static final String DECODERS_MAP_FIELD_NAME = "decoders";

    private final StompSubProtocolHandler delegate;
    /**
     * copy reference from delegate
     */
    private Map<String, BufferingStompDecoder> delegateDecoders;


    public StompSubProtocolHandlerDecorator(StompSubProtocolHandler delegate) {
        Assert.notNull(delegate, "Delegate StompSubProtocolHandler is required");
        this.delegate = delegate;
        try {
            this.delegateDecoders = ReflectionUtil.safeGetFieldValue(delegate, DECODERS_MAP_FIELD_NAME, Map.class);
            this.delegateDecoders = Collections.unmodifiableMap(this.delegateDecoders);
        } catch (Throwable e) {
            log.warn("StompSubProtocolHandlerDecorator 反射获取decoders失败", e);
        }
    }

    @Override
    public void setErrorHandler(StompSubProtocolErrorHandler errorHandler) {
        delegate.setErrorHandler(errorHandler);
    }

    @Override
    public StompSubProtocolErrorHandler getErrorHandler() {
        return delegate.getErrorHandler();
    }

    @Override
    public void setMessageSizeLimit(int messageSizeLimit) {
        delegate.setMessageSizeLimit(messageSizeLimit);
    }

    @Override
    public int getMessageSizeLimit() {
        return delegate.getMessageSizeLimit();
    }

    @Override
    public void setEncoder(StompEncoder encoder) {
        delegate.setEncoder(encoder);
    }

    @Override
    public void setDecoder(StompDecoder decoder) {
        delegate.setDecoder(decoder);
    }

    @Override
    public void setHeaderInitializer(MessageHeaderInitializer headerInitializer) {
        delegate.setHeaderInitializer(headerInitializer);
    }

    @Override
    public MessageHeaderInitializer getHeaderInitializer() {
        return delegate.getHeaderInitializer();
    }

    @Override
    public List<String> getSupportedProtocols() {
        return delegate.getSupportedProtocols();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        delegate.setApplicationEventPublisher(applicationEventPublisher);
    }

    @Override
    public String getStatsInfo() {
        return delegate.getStatsInfo();
    }

    @SuppressWarnings("all")
    @Override
    public void handleMessageFromClient(WebSocketSession session, WebSocketMessage<?> webSocketMessage, MessageChannel outputChannel) {
        try {
            //copy from delegate
            if (webSocketMessage instanceof TextMessage) {
            } else if (webSocketMessage instanceof BinaryMessage) {
            } else {
                return;
            }
            if (null != delegateDecoders) {
                BufferingStompDecoder decoder = delegateDecoders.get(session.getId());
                if (decoder == null) {
                    if (!session.isOpen()) {
                        log.warn("Session id : {}, StompSubProtocolHandlerDecorator Dropped inbound WebSocket message due to closed session", session.getId());
                        return;
                    }
                    log.warn("StompSubProtocolHandlerDecorator session id : {}, decoder not exists, session isOpen: {}", session.getId(), session.isOpen());
                    return;
                }
            }
        } catch (Throwable e) {
            //代理异常就先不处理，交由delegate
            log.warn("StompSubProtocolHandlerDecorator delegate error:", e);
        }
        delegate.handleMessageFromClient(session, webSocketMessage, outputChannel);
    }

    @Override
    public void handleMessageToClient(WebSocketSession session, Message<?> message) {
        delegate.handleMessageToClient(session, message);
    }

    @Override
    protected StompHeaderAccessor toMutableAccessor(StompHeaderAccessor headerAccessor, Message<?> message) {
        return (headerAccessor.isMutable() ? headerAccessor : StompHeaderAccessor.wrap(message));
    }

    @Override
    public String resolveSessionId(Message<?> message) {
        return delegate.resolveSessionId(message);
    }

    @Override
    public void afterSessionStarted(WebSocketSession session, MessageChannel outputChannel) {
        delegate.afterSessionStarted(session, outputChannel);
    }

    @Override
    public void afterSessionEnded(WebSocketSession session, CloseStatus closeStatus, MessageChannel outputChannel) {
        delegate.afterSessionEnded(session, closeStatus, outputChannel);
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return delegate.equals(obj);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }


}
