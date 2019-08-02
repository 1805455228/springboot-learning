package com.hins.sp10rabbitmq.consumer;


import com.hins.sp10rabbitmq.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author qixuan.chen
 * 处理队列C当中的消息
 */
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_C)
public class MsgReceiverC {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String content) {

        logger.info("接收处理队列C当中的消息： {}" , content);
        //TODO 处理你需要实现的业务代码
    }
}
