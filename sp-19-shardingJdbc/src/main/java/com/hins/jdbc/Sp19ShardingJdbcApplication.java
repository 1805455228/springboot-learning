package com.hins.jdbc;

import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(exclude = {SpringBootConfiguration.class})
public class Sp19ShardingJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp19ShardingJdbcApplication.class, args);
    }

}
