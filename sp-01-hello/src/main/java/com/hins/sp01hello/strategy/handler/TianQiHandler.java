package com.hins.sp01hello.strategy.handler;

import org.springframework.stereotype.Component;

@Component
public class TianQiHandler implements Handler {

    @Override
    public void AAA(String name) {
        // 业务逻辑A
        System.out.println("田七完成任务");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Factory.register("田七", this);
    }
}
