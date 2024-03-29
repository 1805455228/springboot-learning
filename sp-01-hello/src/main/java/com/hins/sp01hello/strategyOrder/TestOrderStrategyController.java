package com.hins.sp01hello.strategyOrder;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author qixuan.chen
 * @date 2022/2/7 19:47
 */
@RestController
@RequestMapping("/orderStrategy")
public class TestOrderStrategyController {


    @Autowired
    private OrderPayStrategyFactory orderPayStrategyFactory;

    @GetMapping("/payNotify")
    public String testRequestBody(@RequestParam Integer orderType,@RequestParam Integer groupWay) throws IOException {

        OrderPayStrategyInterface orderPayStrategyInterface = orderPayStrategyFactory.createStrategy(orderType, groupWay);
        if(ObjectUtil.isNull(orderPayStrategyInterface)) {
            return "获取策略失败！";
        }
        String orderId = "11111";
        String xmlData = "";
        Long memberId = 22101L;
        orderPayStrategyInterface.payNotifyOrderFlow(orderType, groupWay, orderId, xmlData, memberId);

        Object obj = null;
        orderPayStrategyInterface.dftPayNotifyOrderFlow(orderType,groupWay,orderId,obj,memberId);

        return "success";
    }

}
