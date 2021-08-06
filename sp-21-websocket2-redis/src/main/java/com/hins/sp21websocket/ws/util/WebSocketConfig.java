package com.hins.sp21websocket.ws.util;

import com.hins.sp21websocket.ws.entity.WsConstant;
import com.hins.sp21websocket.ws.interceptor.WsHandshakeInterceptor;
import com.hins.sp21websocket.ws.interceptor.WsInboundInterceptor;
import com.hins.sp21websocket.ws.interceptor.WsOutboundInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.messaging.StompSubProtocolHandler;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * @author mpg
 * @date 2021/06/24
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Resource
    private ThreadPoolTaskExecutor wsExecutor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //代理原始StompSubProtocolHandler对象
        this.delegateStompSubProtocolHandler(stompEndpointRegistry);
        //注册一个Stomp的节点（endpoint）,并指定使用SockJS协议。
        stompEndpointRegistry.addEndpoint(WsConstant.END_POINT)
                // 设置跨域
                .setAllowedOrigins("*")
                //.setAllowedOriginPatterns("*")
                .setHandshakeHandler(new HandshakeHandler())
                .addInterceptors(new WsHandshakeInterceptor())
        .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //服务端发送消息给客户端的域,多个用逗号隔开
        registry.enableSimpleBroker(WsConstant.TOPIC_PREFIX, WsConstant.USER_DESTINATION_PREFIX);
        registry.setUserDestinationPrefix(WsConstant.USER_DESTINATION_PREFIX);
        registry.setApplicationDestinationPrefixes(WsConstant.APPLICATION_DESTINATION_PREFIX, WsConstant.USER_DESTINATION_PREFIX);
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration
                //设置消息字节数大小
                .setMessageSizeLimit(8192)
                //设置消息缓存大小
                .setSendBufferSizeLimit(8192)
                //设置消息发送时间限制毫秒
                .setSendTimeLimit(1000);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.taskExecutor(wsExecutor);
//        registration.taskExecutor()
//                //设置消息输入通道的线程池线程数
//                .corePoolSize(4)
//                //最大线程数
//                .maxPoolSize(8)
//                //线程活动时间
//                .keepAliveSeconds(60);
        registration.interceptors(webSocketInterceptor());
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor(wsExecutor);
        //registration.taskExecutor().corePoolSize(4).maxPoolSize(8).keepAliveSeconds(60);
        registration.interceptors(wsOutboundInterceptor());
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        //messageConverters.add(new FastJsonMessageConverter());
        return true;
    }

    @Bean
    public WsInboundInterceptor webSocketInterceptor() {
        return new WsInboundInterceptor();
    }

    @Bean
    public WsOutboundInterceptor wsOutboundInterceptor() {
        return new WsOutboundInterceptor();
    }

    /**
     * fix "No decoder for session id" Exception if session is closed
     * Issue <a href="https://github.com/spring-projects/spring-framework/issues/24842">Avoid “No decoder for session id” Exception if session is closed<a/>
     * Spring已在v5.2.6.RELEASE版本修复该问题
     * <p>
     * 代理原始StompSubProtocolHandler
     * 目的是在不调整Spring版本的情况下，修复Spring旧版本中decoder为空的问题
     *
     * @param stompEndpointRegistry stomp端点
     */
    private void delegateStompSubProtocolHandler(StompEndpointRegistry stompEndpointRegistry) {
        Object stompSubObj = ReflectionUtil.safeGetFieldValue(stompEndpointRegistry, WsConstant.STOMP_HANDLER_FIELD_NAME);
        if (!(stompSubObj instanceof StompSubProtocolHandler)) {
            return;
        }
        StompSubProtocolHandler handler = (StompSubProtocolHandler) stompSubObj;
        StompSubProtocolHandlerDecorator decorator = new StompSubProtocolHandlerDecorator(handler);
        ReflectionUtil.safeSetFieldValue(stompEndpointRegistry, WsConstant.STOMP_HANDLER_FIELD_NAME, decorator);
    }
}