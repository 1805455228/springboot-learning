package com.hins.sp01hello.strategy.handler;

import org.springframework.beans.factory.InitializingBean;

/**
 * 策略设计模式
 */
public interface Handler extends InitializingBean {

    public void AAA(String name);

}