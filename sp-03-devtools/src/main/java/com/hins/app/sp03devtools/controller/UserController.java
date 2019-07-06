package com.hins.app.sp03devtools.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qixuan.chen
 * @date 2019-07-05 10:04
 *
 * 热部署测试
 */

@RestController
public class UserController {


    @RequestMapping(value = "/getuser")
    public String getUser(){

        System.out.println("热部署测试.............test");
        String devtools = "热部署测"+"....2222";
        return devtools;
    }



}
