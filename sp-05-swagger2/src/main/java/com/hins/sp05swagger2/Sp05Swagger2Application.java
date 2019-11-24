package com.hins.sp05swagger2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Sp05Swagger2Application {

    public static void main(String[] args) {
        SpringApplication.run(Sp05Swagger2Application.class, args);
    }

}
