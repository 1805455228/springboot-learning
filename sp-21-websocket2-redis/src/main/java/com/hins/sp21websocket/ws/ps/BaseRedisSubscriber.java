package com.hins.sp21websocket.ws.ps;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Description:
 *
 * @author mpg
 * @date 2021/06/25
 */
@Slf4j
public abstract class BaseRedisSubscriber extends RedisSubscriber {


    public void onMessage(String channel, Object message) {
        if (StringUtils.isBlank(channel)) {
            return;
        }
        try {
            onMsg(channel, message);
            log.info("Redis Subscriber channel:{}, msg:{}", channel, message);
        } catch (Exception e) {
            log.error("BaseRedisSubscriber error, channel:{},msg:{}", channel, message, e);
        }
    }


    /**
     * 接收到消息的时候执行
     *
     * @param channel
     * @param message
     */
    public abstract void onMsg(String channel, Object message);

}
