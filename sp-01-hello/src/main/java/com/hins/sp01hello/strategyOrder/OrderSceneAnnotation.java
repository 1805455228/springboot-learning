package com.hins.sp01hello.strategyOrder;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderSceneAnnotation {
    /**
     * 订单类型
     */
    GroupTypeEnum orderType();

    /**
     * 配送类型
     * @return
     */
    GroupWayEnum groupWay();
}
