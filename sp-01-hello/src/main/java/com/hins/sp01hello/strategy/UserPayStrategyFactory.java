package com.hins.sp01hello.strategy;

import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定义一个工厂方法，让工厂方法与不同策略结合起来。在选择不同策略的时候，通过工厂类来创造这个方法
 * @author qixuan.chen
 * @date 2022/2/7 19:37
 */
public class UserPayStrategyFactory {

    private static Map<String,UserPayService> services = new ConcurrentHashMap<>();

    public static UserPayService getByUserType(String type){
        return services.get(type);
    }

    public static void register(String userType,UserPayService userPayService){
        Assert.notNull(userType,"type can't be null");
        services.put(userType,userPayService);
    }
}


