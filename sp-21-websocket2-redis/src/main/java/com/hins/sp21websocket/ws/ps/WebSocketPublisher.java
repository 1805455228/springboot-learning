package com.hins.sp21websocket.ws.ps;

import com.alibaba.fastjson.JSONObject;
import com.hins.sp21websocket.ws.entity.WsConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Description:
 *
 * @author mpg
 * @date 2021/06/25
 */
@Slf4j
@Component
@Validated
public class WebSocketPublisher {


    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Resource
    private SimpUserRegistry simpUserRegistry;

    @Resource
    private RedisPublisher redisPublisher;

    /**
     * 广播
     * 发给所有在线用户
     *
     * @param topic
     * @param msg
     */
    @Async("taskExecutor")
    public void sendBroadcastMessage(@NotBlank String topic, @NotNull @Valid Object msg) {
        String jsonString = JSONObject.toJSONString(msg);
        redisPublisher.publishWsTopic(topic, jsonString);
    }

    /**
     * 发送给指定用户
     *
     * @param msgPath
     * @param userIds
     * @param msg
     */
    @Async("taskExecutor")
    public void send2Users(@NotBlank String msgPath, @NotEmpty List<Long> userIds, @NotNull @Valid Object msg) {
        String jsonString = JSONObject.toJSONString(msg);
        redisPublisher.publishWsUserMsg(msgPath, userIds, jsonString);
    }

    /**
     * 发送给指定用户 禁止业务逻辑调用
     *
     * @param msgPath
     * @param userIds
     * @param jsonStr
     */
    protected void send2UsersToThisServer(@NotBlank String msgPath, @NotEmpty List<Long> userIds, String jsonStr) {
        userIds.forEach(userId -> {
            String userIdStr = userId.toString();
            SimpUser simpUser = simpUserRegistry.getUser(userIdStr);
            if (null == simpUser) {
                return;
            }
            simpMessagingTemplate.convertAndSendToUser(userIdStr, msgPath, jsonStr);
        });
    }

    /**
     * 广播 禁止业务逻辑使用
     * 发给所有在线用户
     *
     * @param topic
     * @param jsonStr
     */
    protected void sendBroadCastMessageToThisServer(@NotBlank String topic, String jsonStr) {
        simpMessagingTemplate.convertAndSend(WsConstant.TOPIC_PREFIX + topic, jsonStr);
    }
}