package com.sp.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Sp16RocketmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sp16RocketmqApplication.class, args);
	}

}
