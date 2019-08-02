package com.hins.sp09redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Sp09RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp09RedisApplication.class, args);
    }

}
