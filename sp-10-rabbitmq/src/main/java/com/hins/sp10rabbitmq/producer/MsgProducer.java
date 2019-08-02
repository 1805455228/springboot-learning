package com.hins.sp10rabbitmq.producer;

import com.hins.sp10rabbitmq.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author 2019年6月25日11:13:24
 * 消息生产者
 */
@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
     */
    private RabbitTemplate rabbitTemplate;
    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMsg(String content) {
        // CorrelationData  该数据的作用是给每条消息一个唯一的标识
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A, RabbitConfig.ROUTINGKEY_A, content, correlationId);
    }


    public void sendMsgToQueueB(String content) {
        // CorrelationData  该数据的作用是给每条消息一个唯一的标识
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_B, RabbitConfig.ROUTINGKEY_B, content, correlationId);
    }


    /**
     * 消息发送，这里不设置routing_key,因为设置了也无效，发送端的routing_key写任何字符都会被忽略。
     * @param content
     */
    public void sendAll(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE,"", content,correlationId);
    }

    /**
     * 回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info(" 回调id:" + correlationData);
        if (ack) {
            logger.info("消息成功被发送到rabbitmq");
        } else {
            logger.info("消息发送到rabbitmq失败:" + cause);
        }
    }
}

