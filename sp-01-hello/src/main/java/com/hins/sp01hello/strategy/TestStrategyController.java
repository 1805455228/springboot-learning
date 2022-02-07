package com.hins.sp01hello.strategy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qixuan.chen
 * @date 2022/2/7 19:47
 */
@RestController
@RequestMapping("/strategy")
public class TestStrategyController {

    //http://localhost:8081/strategy/test


    @GetMapping("/test")
    public Map<String,Object> testRequestBody(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String type1 = "particularlyVip";
        UserPayService userPayService = UserPayStrategyFactory.getByUserType(type1);
        BigDecimal quote = userPayService.quotePrice(new BigDecimal(300));
        System.out.println("particularlyVip会员商品的最终价格为：" + quote.doubleValue());

        String type2 = "Vip";
        UserPayService userPayService2 = UserPayStrategyFactory.getByUserType(type2);
        BigDecimal quote2 = userPayService2.quotePrice(new BigDecimal(300));
        System.out.println("vip会员商品的最终价格为：" + quote2.doubleValue());

        String type3= "SuperVip";
        UserPayService userPayService3 = UserPayStrategyFactory.getByUserType(type3);
        BigDecimal quote3 = userPayService3.quotePrice(new BigDecimal(300));
        System.out.println("SuperVip会员商品的最终价格为：" + quote3.doubleValue());

        Map<String,Object> result = new HashMap<>();
        result.put(type1,quote);
        result.put(type2,quote2);
        result.put(type3,quote3);

        return result;
    }

}
