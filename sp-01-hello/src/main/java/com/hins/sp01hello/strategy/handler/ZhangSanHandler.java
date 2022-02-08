package com.hins.sp01hello.strategy.handler;

import org.springframework.stereotype.Component;

@Component
public class ZhangSanHandler implements Handler {

    @Override
    public void AAA(String name) {
        // 业务逻辑A
        System.out.println("张三完成任务");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Factory.register("张三", this);
    }
}
