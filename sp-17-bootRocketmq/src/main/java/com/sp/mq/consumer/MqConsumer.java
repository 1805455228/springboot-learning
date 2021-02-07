package com.sp.mq.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author : chenqixuan
 * @date : 2021/1/28
 */
@Component
@RocketMQMessageListener(topic = "RLT_TEST_TOPIC", consumerGroup = "consumer-demo1")
public class MqConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        System.out.println("收到： "+s);
    }
}
