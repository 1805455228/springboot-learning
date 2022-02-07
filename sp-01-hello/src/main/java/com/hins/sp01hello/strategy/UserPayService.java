package com.hins.sp01hello.strategy;

import java.math.BigDecimal;

/**
 * 定义一个service的接口，定义不同策略类。让不同策略类实现计算价格的这一接口。
 * @author qixuan.chen
 * @date 2022/2/7 19:33
 */
public interface UserPayService {

    /**
     * 计算应付价格
     */
    BigDecimal quotePrice(BigDecimal orderPrice);
}

