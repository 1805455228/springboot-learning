package com.hins.sp01hello.strategyOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderPayStrategyFactory {
    public static final Map<String, OrderPayStrategyInterface> STRATEGY_BEAN_CACHE = new HashMap<>();
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 根据不同的场景创建不同的策略
     * 实现思路：遍历策略列表的所有策略，获取策略的注解，
     * 比对订单类型，配送类型是否一致，场景值一致则返回当前策略的实例对象
     *
     * @param orderType 订单类型
     * @param groupWay 配送类型
     **/
    public OrderPayStrategyInterface createStrategy(Integer orderType, Integer groupWay) {
        Optional<OrderPayStrategyInterface> strategyOptional =
                STRATEGY_BEAN_CACHE
                        .entrySet()
                        .stream()
                        .map(e -> {
                            OrderSceneAnnotation validScene = e.getValue().getClass().getDeclaredAnnotation(OrderSceneAnnotation.class);
                            if (Objects.equals(validScene.orderType().getValue(),orderType)
                                    && Objects.equals(validScene.groupWay().getValue(),groupWay)) {
                                return e.getValue();
                            }
                            return null;
                        }).filter(Objects::nonNull)
                        .findFirst();
        if(strategyOptional.isPresent()){
            return strategyOptional.get();
        }
        throw new RuntimeException("订单支付后策略获得失败");
    }
    @PostConstruct
    private void init() {
        STRATEGY_BEAN_CACHE.putAll(applicationContext.getBeansOfType(OrderPayStrategyInterface.class));
    }
}
