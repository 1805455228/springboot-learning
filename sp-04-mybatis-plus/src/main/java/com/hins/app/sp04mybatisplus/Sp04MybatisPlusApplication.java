package com.hins.app.sp04mybatisplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class Sp04MybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp04MybatisPlusApplication.class, args);
    }

}
