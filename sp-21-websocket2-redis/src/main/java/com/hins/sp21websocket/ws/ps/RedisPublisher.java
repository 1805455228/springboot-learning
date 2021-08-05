package com.hins.sp21websocket.ws.ps;

import com.alibaba.fastjson.JSONObject;
import com.hins.sp21websocket.api.RedisUtil;
import com.hins.sp21websocket.ws.entity.WsTopicDto;
import com.hins.sp21websocket.ws.entity.WsUserMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * @author mpg
 * @date 2021/06/25
 */
@Component
@Slf4j
public class RedisPublisher {

    @Resource
    private RedisUtil redisUtil;

    public void publishWsTopic(String topic, String jsonStr) {
        WsTopicDto wsTopicDto = new WsTopicDto();
        wsTopicDto.setTopic(topic);
        wsTopicDto.setMsg(jsonStr);
        redisUtil.publish(RedisChannel.WS_TOPIC, JSONObject.toJSONString(wsTopicDto));
    }

    public void publishWsUserMsg(String msgPath, List<Long> memberIds, String jsonStr) {
        WsUserMsgDto wsUserMsgDto = new WsUserMsgDto();
        wsUserMsgDto.setMsgPath(msgPath);
        wsUserMsgDto.setMemberIds(memberIds);
        wsUserMsgDto.setMsg(jsonStr);
        redisUtil.publish(RedisChannel.WS_USER_MSG, JSONObject.toJSONString(wsUserMsgDto));
    }
}
