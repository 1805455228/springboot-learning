package com.hins.sp10rabbitmq.consumer;

import com.hins.sp10rabbitmq.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author qixuan.chen
 * 准备了三个类MsgReceiverB_A，MsgReceiverB_B，MsgReceiverB_C，来消费队列B当中的消息，消费的顺序是负载均衡的
 * 消费的顺序是无序的，也就是不保证先进来的消息先被消费
 */
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_B)
public class MsgReceiverB_C {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String content) {
        logger.info("处理器C接收处理队列B当中的消息： {}" , content);
        //TODO 处理你需要实现的业务代码
    }
}
