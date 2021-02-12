package com.hins.sp18shardingsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
public class Sp18ShardingsphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp18ShardingsphereApplication.class, args);
    }

}
