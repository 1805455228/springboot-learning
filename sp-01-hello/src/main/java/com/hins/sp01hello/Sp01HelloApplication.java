package com.hins.sp01hello;

import com.hins.sp01hello.controller.HelloController;
import com.hins.sp01hello.springbean.SpringBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Sp01HelloApplication {

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Sp01HelloApplication.class, args);





        // 测试spring ioc bean的生命周期
//        ConfigurableApplicationContext context = SpringApplication.run(Sp01HelloApplication.class, args);
//        HelloController bean1 = context.getBean(HelloController.class);
//        HelloController bean2 = (HelloController) context.getBean("helloController");

//        SpringBean bean1 = context.getBean(SpringBean.class);
//        SpringBean bean2 = (SpringBean) context.getBean("springBean");
//
//        System.out.println(bean1);
//        System.out.println(bean2);
    }

}
