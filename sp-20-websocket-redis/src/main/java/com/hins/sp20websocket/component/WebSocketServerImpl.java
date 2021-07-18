package com.hins.sp20websocket.component;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : chenqixuan
 * @date : 2021/4/29
 */
@Service
public class WebSocketServerImpl implements WebSocketServer {

    @Autowired
    RedisTemplate<String,String> redisTemplateMap;


    @Override
    public void sendMessageAll(String projectId, String message) {
        SendMsgAll sendMsgAll = new SendMsgAll();
        sendMsgAll.setProjectId(projectId);
        sendMsgAll.setMsg(message);
        redisTemplateMap.convertAndSend("mh-topic", JSON.toJSONString(sendMsgAll));
    }


    @Override
    public void sendMessageById(String projectId, String userId, String message) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setProjectId(projectId);
        sendMsg.setUserId(userId);
        sendMsg.setMsg(message);
        redisTemplateMap.convertAndSend("ptp-topic",JSON.toJSONString(sendMsg));
    }
}