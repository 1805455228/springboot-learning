package com.hins.sp01hello.strategyOrder.orderHandler;

import com.hins.sp01hello.strategyOrder.GroupTypeEnum;
import com.hins.sp01hello.strategyOrder.GroupWayEnum;
import com.hins.sp01hello.strategyOrder.OrderPayStrategyInterface;
import com.hins.sp01hello.strategyOrder.OrderSceneAnnotation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 陌生人拼团+ 外卖
 */
@Slf4j
@Service
@OrderSceneAnnotation(orderType = GroupTypeEnum.GROUP_TWO, groupWay = GroupWayEnum.TAKE_OUT)
public class MoShengPeoAndWaiMaiPayOrderFlow implements OrderPayStrategyInterface {




    /**
     * 陌生人拼团+ 外卖
     * @param orderType 订单类型
     * @param groupWay 配送方式
     * @param orderId 订单Id
     * @param xmlData 微信返回报文
     */
    @SneakyThrows
    @Override
    public void payNotifyOrderFlow(Integer orderType, Integer groupWay, String orderId, String xmlData, Long memberId) {
        log.info("微信支付：{},{}",GroupTypeEnum.getDescription(orderType),GroupWayEnum.getDescription(groupWay));
    }

    /**
     * 陌生人拼团+ 自取
     * @param orderType 订单类型
     * @param groupWay 配送方式
     * @param orderId 订单Id
     * @param res 鼎付通返回报文
     */
    @SneakyThrows
    @Override
    public void dftPayNotifyOrderFlow(Integer orderType, Integer groupWay, String orderId, Object res, Long memberId) {
        log.info("鼎付通支付：{},{}",GroupTypeEnum.getDescription(orderType),GroupWayEnum.getDescription(groupWay));
    }


}
