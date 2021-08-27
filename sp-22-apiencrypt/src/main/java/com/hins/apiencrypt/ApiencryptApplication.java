package com.hins.apiencrypt;

import cn.licoy.encryptbody.annotation.EnableEncryptBody;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEncryptBody
@SpringBootApplication
public class ApiencryptApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiencryptApplication.class, args);
    }

}
