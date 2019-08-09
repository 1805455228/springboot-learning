package com.hins.sp10rabbitmq.controller;

import com.hins.sp10rabbitmq.config.RabbitConfig;
import com.hins.sp10rabbitmq.entity.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author qixuan.chen
 * @date 2019-08-05 21:21
 */
@RestController
public class ScheduleSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @RequestMapping(value="/send1",method = {RequestMethod.POST,RequestMethod.GET})
    public String send1(){
        User user = new User();
        user.setId("111");
        user.setEmail("1805455228@qq.com");
        user.setUsername("xuan你是");

        send(user, 5*1000);//发送消息
        return "success";
    }

    @RequestMapping(value="/send2",method = {RequestMethod.POST,RequestMethod.GET})
    public String send2(){
        User user = new User();
        user.setId("222");
        user.setEmail("1805455228@qq.com");
        user.setUsername("xuan你是1222222");

        send(user, 30*1000);//发送消息
        return "success";
    }

    @RequestMapping(value="/send3",method = {RequestMethod.POST,RequestMethod.GET})
    public String send3(){
        User user = new User();
        user.setId("333");
        user.setEmail("1805455228@qq.com");
        user.setUsername("xuan你是1222222");

        send2(user, 60*1000);//发送消息
        return "success";
    }

    /**
     * 发送消息
     * @param user
     * @param delayTime
     */
    public void send(User user, int delayTime) {
        System.out.println("delayTime" + delayTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.rabbitTemplate.convertAndSend(RabbitConfig.TTL_EXCHANGE, RabbitConfig.TTL_ROUTINGKEY, user, message -> {
            message.getMessageProperties().setExpiration(delayTime + "");
            System.out.println(sdf.format(new Date()) + " Delay sent.");
            return message;
        });
    }

    /**
     * 发送消息
     * @param user
     * @param delayTime
     */
    public void send2(User user, int delayTime) {
        System.out.println("delayTime" + delayTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.rabbitTemplate.convertAndSend(RabbitConfig.TTL_EXCHANGE, RabbitConfig.TTL_ROUTINGKEY, user, message -> {
            message.getMessageProperties().setDelay(delayTime);
            System.out.println(sdf.format(new Date()) + " Delay sent.");
            return message;
        });
    }


}
