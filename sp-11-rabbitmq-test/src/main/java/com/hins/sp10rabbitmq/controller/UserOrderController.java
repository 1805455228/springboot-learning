package com.hins.sp10rabbitmq.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hins.sp10rabbitmq.config.RabbitConfig;
import com.hins.sp10rabbitmq.entity.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author qixuan.chen
 * @date 2019-08-03 11:47
 */
@RestController
public class UserOrderController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;



    @RequestMapping(value="/order",method = {RequestMethod.POST,RequestMethod.GET})
    public String login(){

        try {
            //TODO：执行下单、抢单逻辑、入库

            //TODO：发送订单消息
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setEmail("1805455228@qq.com");
            user.setUsername("xuan你是");

            //发送抢单消息 到用户订单队列 (秒杀下单利用mq限流)
            byte[] msg = objectMapper.writeValueAsString(user).getBytes("UTF-8");
            rabbitTemplate.setExchange(RabbitConfig.USER_ORDER_EXCHANGE);
            rabbitTemplate.setRoutingKey(RabbitConfig.USER_ORDER_ROUTINGKEY);

            Message message = MessageBuilder.withBody(msg).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
            rabbitTemplate.send(message);

        }catch (Exception e){
            System.out.println("发送抢单消息，出现异常");
            e.printStackTrace();
        }
        System.out.println("发送抢单消息完成！----------");
        return "success";
    }
}
