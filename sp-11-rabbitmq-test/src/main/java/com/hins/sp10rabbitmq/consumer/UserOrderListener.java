package com.hins.sp10rabbitmq.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author qixuan.chen
 * @date 2019-08-03 11:25
 */
@Component
public class UserOrderListener implements ChannelAwareMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(UserOrderListener.class);

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private UserLogMapper userLogMapper;


    /**
     * 手动确认消息，假如不确认的话，消息一直会存在在队列当中，下次消费的时候，就会出现重复消费
     * @param channel
     * @param message
     */
    @Override
    public void onMessage( Message message,Channel channel) throws Exception{

        logger.info("=====接收处理队列A当中的消息： {}" , "");
        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        try {

            byte[] body = message.getBody();
            String mobile = new String(body,"UTF-8");
            logger.info("--------接收到消息：{}"+mobile);

            //TODO 处理你需要实现的业务代码

            //确认消费
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (IOException e) {
            logger.info("用户抢单发生异常：{}");
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            e.printStackTrace();
        }
    }
}
