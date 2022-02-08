package com.hins.sp01hello.strategyOrder;



/**
 * 订单支付后流程（不同类型订单走不同的流程）
 */
public interface OrderPayStrategyInterface {
    /**
     *
     * @param orderType 订单类型
     * @param groupWay 配送方式
     * @param orderId 订单Id
     * @param xmlData 微信返回报文
     */
    void payNotifyOrderFlow(Integer orderType, Integer groupWay, String orderId, String xmlData, Long memberId);

    /**
     * 鼎付通支付回调
     * @param orderType
     * @param groupWay
     * @param orderId
     * @param memberId
     * @param res 鼎付通返回报文
     */
    void dftPayNotifyOrderFlow(Integer orderType, Integer groupWay, String orderId,Object res, Long memberId);
}
