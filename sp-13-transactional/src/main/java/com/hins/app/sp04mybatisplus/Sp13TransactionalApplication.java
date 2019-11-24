package com.hins.app.sp04mybatisplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Sp13TransactionalApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp13TransactionalApplication.class, args);
    }

}
