package com.hins.sp08netty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qixuan.chen
 * @date 2019-07-18 15:45
 */
@Controller
public class ChatController {

    @RequestMapping("/chat")
    public String chat(HttpServletRequest req) {
        return "chat";
    }
}
