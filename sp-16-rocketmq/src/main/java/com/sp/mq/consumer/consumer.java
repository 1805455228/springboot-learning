package com.sp.mq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : chenqixuan
 * @date : 2021/1/25
 */
@Component
public class consumer {

    @Resource
    private DefaultMQPushConsumer consumer;

    /**
     * 接收mq消息
     */
    private void receiveMsgToMq() {
        String str = "接收测试消息";
        Message msg;
        try {
            consumer = new DefaultMQPushConsumer("TopicTest007Group");
            consumer.setNamesrvAddr("27.0.0.1:9876");
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.subscribe("TopicTest007", "*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                ConsumeConcurrentlyContext context) {
                    for(MessageExt msg:msgs){
                        System.out.println("RocketMQConsumer2："+new String(msg.getBody()));
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
            System.out.printf("Consumer Started.%n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
