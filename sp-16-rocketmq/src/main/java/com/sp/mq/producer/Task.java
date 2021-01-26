package com.sp.mq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

import javax.annotation.Resource;


/**
 * @author : chenqixuan
 * @date : 2021/1/25
 */
@Component
public class Task {
    @Resource
    private DefaultMQProducer producer;

    /**
     * 每5秒执行一次
     * 由于使用了@Scheduled的定时任务注解，在Application类上也需要添加一个@EnableScheduling注解，才能使定时任务生效。
     */
    @Scheduled(cron = "0/5 * *  * * ?")
    private void sendMsgToMq() {
        String str = "发送测试消息";
        Message msg;
        try {
            msg = new Message("test-demo"
                    , "111"
                    , UUID.randomUUID().toString()
                    , str.getBytes("utf-8"));
            SendResult result = producer.send(msg);
            if (result.getSendStatus() == SendStatus.SEND_OK) {
                System.out.println("消息发送成功，你大爷已经更新版本，赶紧去下载新系统吧！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
