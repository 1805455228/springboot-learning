package com.hins.sp10rabbitmq.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.UUID;

/**
 * @author qixuan.chen
 * @date 2019-08-02 23:28
 */

@RestController
public class RegisterController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;



    @RequestMapping(value="/register",method = {RequestMethod.POST,RequestMethod.GET})
    public String login(){

        try {
            //TODO：执行注册逻辑

            //TODO：异步发送邮件
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setEmail("1805455228@qq.com");
            user.setUsername("xuan你是");


            byte[] msg = objectMapper.writeValueAsString(user).getBytes("UTF-8");
            rabbitTemplate.setExchange(RabbitConfig.EMAIL_EXCHANGE);
            rabbitTemplate.setRoutingKey(RabbitConfig.EMAIL_ROUTINGKEY);
            rabbitTemplate.convertAndSend(MessageBuilder.withBody(msg).build());

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("邮件发送完毕！----------");
        return "success";
    }
}
