package com.hins.sp21websocket.ws.util;

import com.hins.sp21websocket.api.RedisUtil;
import com.hins.sp21websocket.ws.ps.RedisChannel;
import com.hins.sp21websocket.ws.ps.RedisSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mpg
 * @since 2021/6/28
 */
@Configuration
public class MessageConfig {

    @Resource
    private RedisSubscriber redisSubscriber;
    @Resource
    private RedisUtil redisUtil;

    @Bean
    public RedisMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisUtil.getConnectionFactory());
        List<Topic> topicList = new ArrayList<>();
        topicList.add(new PatternTopic(RedisChannel.WS_TOPIC));
        topicList.add(new PatternTopic(RedisChannel.WS_USER_MSG));
        container.addMessageListener(listenerAdapter, topicList);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(redisSubscriber);
    }

}
