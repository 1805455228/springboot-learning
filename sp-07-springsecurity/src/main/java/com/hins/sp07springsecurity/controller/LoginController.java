package com.hins.sp07springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author qixuan.chen
 * @date 2019-07-13 10:12
 */

@Controller
public class LoginController {

    // Login form
    @RequestMapping("/login")
    public String login() {
        System.out.println("自定义登录");
        return "login";
    }

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping("/sys/login")
    public String loginPage(){
        System.out.println("自定义登录");
        return "login";
    }
}
