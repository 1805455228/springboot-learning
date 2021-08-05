package com.hins.sp21websocket.ws.entity;

/**
 * Description:
 *
 * @author mpg
 * @date 2021/6/25
 */
public interface WsConstant {

    /**
     * 定义一个Stomp的节点（endpoint）
     */
    String END_POINT = "/ws/endPoint";

    /**
     * 广播前缀
     */
    String TOPIC_PREFIX = "/ws/topic";

    /**
     * 一对一推送的前缀
     */
    String USER_DESTINATION_PREFIX = "/ws/user";

    /**
     * 定义websoket消息前缀，前端发送的ws信息中，需要以此为前缀
     */
    String APPLICATION_DESTINATION_PREFIX = "/ws/app";

    String STOMP_HANDLER_FIELD_NAME = "stompHandler";

    String TOKEN = "ws-token";

}
