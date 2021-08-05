package com.hins.sp21websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@SpringBootApplication
public class Sp21WebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp21WebsocketApplication.class, args);
    }



}
