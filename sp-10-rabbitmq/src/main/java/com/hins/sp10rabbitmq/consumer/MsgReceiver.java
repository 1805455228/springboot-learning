package com.hins.sp10rabbitmq.consumer;


import com.hins.sp10rabbitmq.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;

import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * @author 2019年6月25日11:32:49
 * 消费者
 */
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class MsgReceiver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 手动确认消息，假如不确认的话，消息一直会存在在队列当中，下次消费的时候，就会出现重复消费
     * @param content
     * @param channel
     * @param message
     */
    @RabbitHandler
    public void process(String content,Channel channel, Message message) {
        logger.info("接收处理队列A当中的消息： {}" , content);
        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        try {
            //TODO 处理你需要实现的业务代码

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);


        } catch (IOException e) {
            //丢弃这条消息
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            e.printStackTrace();
        }
    }


    /*
    最简单的消息消费功能
    @RabbitHandler
    public void process(String content) {
        logger.info("接收处理队列A当中的消息： {}" , content);
    }*/

}

