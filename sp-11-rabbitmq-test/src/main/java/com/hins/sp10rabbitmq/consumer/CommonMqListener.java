package com.hins.sp10rabbitmq.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hins.sp10rabbitmq.config.RabbitConfig;
import com.hins.sp10rabbitmq.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author qixuan.chen
 * @date 2019-08-02 23:11
 */
@Component
public class CommonMqListener {

    private static final Logger log = LoggerFactory.getLogger(CommonMqListener.class);

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private UserLogMapper userLogMapper;

//    @Autowired
//    private MailService mailService;

    /**
     * 监听消费用户日志
     *
     * @param message
     */
    @RabbitListener(queues = RabbitConfig.LOG_USER_QUEUE, containerFactory = "singleListenerContainer")
    public void consumeUserLogQueue(@Payload byte[] message) {
        try {
            //UserLog userLog=objectMapper.readValue(message, UserLog.class);
            User userMsg=objectMapper.readValue(message, User.class);

            log.info("监听消费用户日志 监听到消息： {} ", userMsg);

            //TODO：记录日志入数据表
            //userLogMapper.insertSelective(userLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听邮件发送
     * @param message
     */
    @RabbitListener(queues = RabbitConfig.EMAIL_QUEUE, containerFactory = "singleListenerContainer")
    public void consumeEmailQueue(@Payload byte[] message) {
        try {

            log.info("监听消费发送邮件 监听到消息： {} ", new String(message,"UTF-8"));

            //TODO：发送邮件业务
            //mailService.sendEmail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
