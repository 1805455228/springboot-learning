package com.hins.sp09redis.common.constant.redis;

/**
 * 订单服务redis key
 * @author zhangyong
 */
public class OrderRedisConstant {

    public static final int MINUTE = 60;
    public static final int HOUR = MINUTE * 60;

    /**
     * 用户指定商品key
     * cart:skuInfo:memberId:storeId:skuId
     */
    public static final String CART_SKU_INFO_KEY = "cart:skuInfo:%s:%s:%s";

    /**
     * 用户购物车key
     * cart:skuIds:memberId:storeId
     */
    public static final String CART_SKU_IDS_KEY = "cart:skuIds:%s:%s";

    /**
     * 用户购物车指定商品数量key
     * cart:skuNum:memberId:storeId:skuId
     */
    public static final String CART_SKU_NUM_KEY = "cart:skuNum:%s:%s:%s";

    /**
     * 海鼎库存缓存key
     * hd:realStock:storeId:skuId
     */
    public static final String HD_REAL_STOCK_KEY = "hd:realStock:%s:%s";

    /**
     * 海鼎流水缓存key
     * hd:waterNumber:门店:日期
     */
    public static final String HD_WATER_NUMBER_KEY = "hd:waterNumber:%s:%s";

    /**
     * 海鼎订单回调锁key
     * hd:orderChange:lock:消息id
     */
    public static final String HD_ORDER_CHANGE_LOCK_KEY = "hd:orderChange:lock:%s";

    /**
     * 顺丰同城达订单回调锁key
     * tcd:orderChange:lock:订单号:订单状态
     */
    public static final String TCD_ORDER_CHANGE_LOCK_KEY = "tcd:orderChange:lock:%s:%s";

    /**
     * 订单id
     * generate_order_id_key: + date + storeCord
     */
    public static final String GENERATE_ORDER_ID_KEY = "generate_order_id_key:";

    /**
     * 订单详情
     * order_detail_key：+ orderId
     */
    public static final String ORDER_DETAIL_KEY = "order_detail_key:";

    /**
     * 取消订单缓存锁
     * order:cancel:lock:订单号
     */
    public static final String ORDER_CANCEL_LOCK_KEY = "order:cancel:lock:%s";

    /**
     * 取消盲盒订单缓存锁
     * order:cancel:lock:订单号
     */
    public static final String BOX_ORDER_CANCEL_LOCK_KEY = "box:order:cancel:lock:%s";

    /**
     * 下单接口校验频繁key
     * generate_order_lock_key:mId
     */
    public static final String GENERATE_ORDER_LOCK_KEY = "generate_order_lock_key:%s";

    /**
     * 取消部分退款的次数
     * cancel_partial_return_time：orderId
     */
    public static final String CANCEL_PARTIAL_RETURN_TIME = "cancel_partial_return_time:%s";

    /**
     * 订单id(盲盒)
     * generate_mystery_box_order_id_key: + date + storeCord
     */
    public static final String GENERATE_MYSTERY_BOX_ORDER_ID_KEY = "generate_mystery_box_order_id_key:";

    /**
     * 订单id(积分兑换)
     * generate_point_exchange_order_id_key: + date
     */
    public static final String GENERATE_POINT_EXCHANGE_ORDER_ID_KEY = "generate_point_exchange_order_id_key:";

    /**
     * RedemptionNo(积分兑换)
     * generate_point_exchange_order_id_key: + date
     */
    public static final String GENERATE_POINT_EXCHANGE_REDEMPTION_NO_KEY = "generate_point_exchange_redemption_no_key:";

    /**
     * 订单详情
     * mystery_box_order_detail_key：+ orderId
     */
    public static final String MYSTERY_BOX_ORDER_DETAIL_KEY = "mystery_box_order_detail_key:";

    /**
     * 支付回调key
     * wx_notify_box_pay_key:mysteryBoxOrderId
     */
    public static final String WX_PAY_NOTIFY_BOX_PAY_KEY = "wx_pay_notify_box_pay_key:%s";

    /**
     * 退款回调key
     * wx_notify_box_pay_key:mysteryBoxOrderId
     */
    public static final String WX_REFUND_NOTIFY_BOX_PAY_KEY = "wx_refund_notify_box_pay_key:%s";

    /**
     * 预抽取滞销品sku
     * product:mix:sku:storeId:skuToken
     */
    public static final String PRODUCT_MIX_SKU = "product:mix:sku:%s:%s";

    /**
     * 获取滞销品sku
     * product:mix:sku:storeId
     */
    public static final String PRODUCT_MIX_SKU_GET = "product:mix:sku:%s";

    /**
     * 暂停寻找盲盒sku组合
     * product:mix:sku:storeId
     */
    public static final String PRODUCT_STORE_STOP = "product:store:stop:%s";

    /**
     * 预抽sku组合 redis 锁
     * product:mix:sku:storeId
     */
    public static final String PRODUCT_STORE_SEARCH_LOCK = "product:store:search:lock:%s";

    /**
     * 中通回调幂等校验
     * redis_zt_order_delivery：status: billCode
     */
    public static final String ZTO_ORDER_DELIVERY = "zto_order_delivery:%s:%s";

    public static final String ZTO_PRE_ORDER_STATE = "zto_pre_order_state:%s:%s";


}
