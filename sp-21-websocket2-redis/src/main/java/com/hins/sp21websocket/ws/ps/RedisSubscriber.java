package com.hins.sp21websocket.ws.ps;

import com.alibaba.fastjson.JSONObject;
import com.hins.sp21websocket.api.RedisUtil;
import com.hins.sp21websocket.exception.ApiException;
import com.hins.sp21websocket.ws.entity.WsTopicDto;
import com.hins.sp21websocket.ws.entity.WsUserMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Description:
 *
 * @author mpg
 * @date 2021/06/25
 */
@Slf4j
@Component
public class RedisSubscriber implements MessageListener {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private WebSocketPublisher webSocketPublisher;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisSerializer<?> serializer = redisUtil.getValueSerializer();
        String channel = (String) serializer.deserialize(message.getChannel());
        Object body = serializer.deserialize(message.getBody());
        if (null == body) {
            return;
        }
        switch (Objects.requireNonNull(channel)) {
            case RedisChannel.WS_TOPIC:
                WsTopicDto wsTopicDto = JSONObject.parseObject(body.toString(), WsTopicDto.class);
                webSocketPublisher.sendBroadCastMessageToThisServer(wsTopicDto.getTopic(), wsTopicDto.getMsg());
                break;
            case RedisChannel.WS_USER_MSG:
                WsUserMsgDto wsUserMsgDto = JSONObject.parseObject(body.toString(), WsUserMsgDto.class);
                webSocketPublisher.send2UsersToThisServer(wsUserMsgDto.getMsgPath(), wsUserMsgDto.getMemberIds(), wsUserMsgDto.getMsg());
                break;
            default:
                throw new ApiException("错误的消息");
        }
//        log.info("主题: " + channel);
//        log.info("消息内容: " + body);
    }

}
