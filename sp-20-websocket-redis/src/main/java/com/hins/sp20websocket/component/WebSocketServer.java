package com.hins.sp20websocket.component;

/**
 * @author : chenqixuan
 * @date : 2021/4/29
 */
public interface WebSocketServer {

    /**
     * 向所有在线用户群发消息
     * @param message 发送给客户端的消息
     */
    void sendMessageAll(String projectId,String message);

    /**
     * 发送给对应的用户
     * @param userId 用户的ID
     * @param message 发送的消息
     */
    void sendMessageById(String projectId,String userId, String message);

}
