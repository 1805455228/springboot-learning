package com.hins.sp10rabbitmq.consumer;

import com.hins.sp10rabbitmq.config.RabbitConfig;
import com.hins.sp10rabbitmq.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听转发队列，并进行业务处理
 * @author qixuan.chen
 * @date 2019-08-05 20:50
 */

@Component
@RabbitListener(queues = RabbitConfig.DELAY_QUEUE)
public class ScheduleReceiver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;



    @RabbitHandler
    public void receive(User content) {
        logger.info("=======收到====延迟====发送的消息： {}" , content);
        //TODO 处理你需要实现的业务代码
    }


}
