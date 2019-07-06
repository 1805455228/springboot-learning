package com.hins.sp01hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qixuan.chen
 * @date 2019-06-27 21:26
 */
@RestController
@RequestMapping("hello")
public class HelloController {


    @GetMapping("val")
    public String getHelloData() {
        Long t = System.currentTimeMillis();
        return t.toString();
    }
}
