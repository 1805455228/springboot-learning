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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author qixuan.chen
 * @date 2019-08-02 22:29
 */

@RestController
public class LoginController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;



    @RequestMapping(value="/login",method = {RequestMethod.POST,RequestMethod.GET})
    public String login(){

        try {
            //TODO：执行登录逻辑
            //User user=userMapper.selectByUserNamePassword(userName,password);

            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setEmail("1805455228@qq.com");
            user.setUsername("xuan日志");

            if (user!=null){
                //TODO：异步写用户日志
                try {
                    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                    rabbitTemplate.setExchange(RabbitConfig.LOG_USER_EXCHANGE);
                    rabbitTemplate.setRoutingKey(RabbitConfig.LOG_USER_ROUTINGKEY);

                    Message message= MessageBuilder.withBody(objectMapper.writeValueAsBytes(user)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                    message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON);
                    rabbitTemplate.convertAndSend(message);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }


}
