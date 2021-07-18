package com.hins.sp20websocket.component;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 处理订阅redis的消息
 * @author : chenqixuan
 * @date : 2021/4/29
 */
@Slf4j
@Component
public class RedisReceiver  {

    @Resource
    WebsocketEndpoint websocketEndpoint;

    /**
     * 处理一对一消息
     * @param message 消息队列中的消息
     */
    public void sendMsg(String message) {
        log.info("redis订阅者收到推送信息：{}",message);
        Object obj = JSONObject.parse(message);
        SendMsg msg = JSONObject.parseObject(obj.toString(), SendMsg.class);
        websocketEndpoint.sendMessageById(msg.getProjectId(),msg.getUserId(),msg.getMsg());
    }

    /**
     * 处理广播消息
     * @param message
     */
    public void sendAllMsg(String message){
        log.info("redis订阅者收到推送信息：{}",message);
        Object obj = JSONObject.parse(message);
        SendMsgAll msg = JSONObject.parseObject(obj.toString(), SendMsgAll.class);
        websocketEndpoint.batchSendMessage(msg.getProjectId(),msg.getMsg());
    }
}