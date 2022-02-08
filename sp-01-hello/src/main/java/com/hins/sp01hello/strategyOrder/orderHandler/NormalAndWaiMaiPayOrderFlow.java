package com.hins.sp01hello.strategyOrder.orderHandler;

import com.hins.sp01hello.strategyOrder.GroupTypeEnum;
import com.hins.sp01hello.strategyOrder.GroupWayEnum;
import com.hins.sp01hello.strategyOrder.OrderPayStrategyInterface;
import com.hins.sp01hello.strategyOrder.OrderSceneAnnotation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 普通单+外卖微信支付后流程
 */
@Service
@Slf4j
@OrderSceneAnnotation(orderType = GroupTypeEnum.NORMAL, groupWay = GroupWayEnum.TAKE_OUT)
public class NormalAndWaiMaiPayOrderFlow implements OrderPayStrategyInterface{


    /**
     * 普通单+外卖微信支付后流程
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
     * 普通单+外卖微信支付后流程
     * @param orderType 订单类型
     * @param groupWay 配送方式
     * @param orderId 订单Id
     * @param res 鼎付通返回报文
     */
    @SneakyThrows
    @Override
    public void dftPayNotifyOrderFlow(Integer orderType, Integer groupWay, String orderId,  Object res, Long memberId) {
        log.info("鼎付通支付：{},{}",GroupTypeEnum.getDescription(orderType),GroupWayEnum.getDescription(groupWay));
    }


}
