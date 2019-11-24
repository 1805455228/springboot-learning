package com.hins.sp06thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author qixuan.chen
 * @date 2019-07-07 13:35
 */
@Controller
@RequestMapping("/front")
public class UserController {

    @RequestMapping("/user")
    public String home(){

        return "index";
    }

}
