package com.sp.mq.controller;

import com.sp.mq.producer.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : chenqixuan
 * @date : 2021/1/28
 */
@RestController
@RequestMapping("/mq")
public class MqTestController {

    @Autowired
    private MqProducer mqProducer;

    @GetMapping("/send")
    public String mqSend(){

        mqProducer.sendB();
        return "sssss";
    }
}
