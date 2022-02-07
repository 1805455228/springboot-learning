package com.hins.sp01hello.strategy;

import org.springframework.beans.factory.InitializingBean;

import java.math.BigDecimal;

/**
 * 定义一个抽象模板类，实现service和InitializingBean 接口，
 * afterPropertiesSet用于注册到工厂方法之中。
 * 这样剩下的策略方法只用继承UserPayTemplate ，实现quotePrice这个方法。
 * @author qixuan.chen
 * @date 2022/2/7 19:34
 */
public abstract class UserPayTemplate implements UserPayService, InitializingBean {

    private String type;

    public UserPayTemplate(String type) {
        this.type = type;
    }

    @Override
    public abstract BigDecimal quotePrice(BigDecimal orderPrice);

    @Override
    public void afterPropertiesSet() throws Exception {
        UserPayStrategyFactory.register(type,this);
    }
}

