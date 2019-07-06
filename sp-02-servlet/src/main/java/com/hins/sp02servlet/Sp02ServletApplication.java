package com.hins.sp02servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 通过注解扫描完成servlet组件注册 （方法一）
 */
@SpringBootApplication
@ServletComponentScan
public class Sp02ServletApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp02ServletApplication.class, args);
    }

}
