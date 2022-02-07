package com.hins.sp01hello.strategy.payprice;

import com.hins.sp01hello.strategy.UserPayTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author qixuan.chen
 * @date 2022/2/7 19:40
 */
@Service
public class SuperVipPayService extends UserPayTemplate {

    private static final String type = "SuperVip";

    public SuperVipPayService() {
        super(type);
    }

    @Override
    public BigDecimal quotePrice(BigDecimal orderPrice) {
        return orderPrice.multiply(new BigDecimal(0.8));
    }
}

