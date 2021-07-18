package com.hins.sp09redis.common.constant.mq;

/**
 * 常用全局队列常量
 * @author zhangyong
 */
public class MqConfig {

    /**
     * 创建海鼎订单队列
     */
    public static final String HD_ORDER_CREATE_QUEUE = "hd.order.create.queue";
    /**
     * 创建海鼎订单交换机
     */
    public static final String HD_ORDER_CREATE_EXCHANGE = "hd.order.create.exchange";
    /**
     * 创建海鼎盲盒订单路由键
     */
    public static final String HD_ORDER_CREATE_KEY = "hd.order.create.key";

    /**
     * 创建海鼎 盲盒 订单队列
     */
    public static final String HD_MYSTERY_BOX_ORDER_CREATE_QUEUE = "hd.mystery.box.order.create.queue";
    /**
     * 创建海鼎 盲盒 订单交换机
     */
    public static final String HD_MYSTERY_BOX_ORDER_CREATE_EXCHANGE = "hd.mystery.box.order.create.exchange";
    /**
     * 创建海鼎 盲盒 订单路由键
     */
    public static final String HD_MYSTERY_BOX_ORDER_CREATE_KEY = "hd.mystery.box.order.create.key";

    /**
     * 创建顺丰同城达订单队列
     */
    public static final String TCD_ORDER_CREATE_QUEUE = "tcd.order.create.queue";
    /**
     * 创建顺丰同城达订单交换机
     */
    public static final String TCD_ORDER_CREATE_EXCHANGE = "tcd.order.create.exchange";
    /**
     * 创建顺丰同城达订单路由键
     */
    public static final String TCD_ORDER_CREATE_KEY = "tcd.order.create.key";

    /**
     * 同步商品信息队列
     */
    public static final String SYNC_SKU_QUEUE = "sync.sku.queue";
    /**
     * 同步商品信息交换机
     */
    public static final String SYNC_SKU_EXCHANGE = "sync.sku.exchange";
    /**
     * 同步商品路由键
     */
    public static final String SYNC_SKU_KEY = "sync.sku.key";

    /**
     * 同步spu信息队列
     */
    public static final String SYNC_SPU_QUEUE = "sync.spu.queue";
    /**
     * 同步spu信息交换机
     */
    public static final String SYNC_SPU_EXCHANGE = "sync.spu.exchange";
    /**
     * 同步spu路由键
     */
    public static final String SYNC_SPU_KEY = "sync.spu.key";

    /**
     * 同步门店商品信息队列
     */
    public static final String SYNC_STORE_SKU_QUEUE = "sync.store.sku.queue";
    /**
     * 同步门店商品信息交换机
     */
    public static final String SYNC_STORE_SKU_EXCHANGE = "sync.store.sku.exchange";
    /**
     * 同步门店商品信息路由键
     */
    public static final String SYNC_STORE_SKU_KEY = "sync.store.sku.key";

    /**
     * 同步门店商品库存或价格信息队列
     */
    public static final String SYNC_STORE_SKU_STOCK_PRICE_QUEUE = "sync.store.sku.stock.price.queue";
    /**
     * 同步门店商品库存或价格信息交换机
     */
    public static final String SYNC_STORE_SKU_STOCK_PRICE_EXCHANGE = "sync.store.sku.stock.price.exchange";
    /**
     * 同步门店商品库存或价格信息路由键
     */
    public static final String SYNC_STORE_SKU_STOCK_PRICE_KEY = "sync.store.sku.stock.price.key";

    /**
     * 类目同步队列
     */
    public static final String SYNC_PLATFORM_CATEGORY_QUEUE = "sync.platform.category.queue";
    /**
     * 类目同步交换机
     */
    public static final String SYNC_PLATFORM_CATEGORY_EXCHANGE = "sync.platform.category.exchange";
    /**
     * 类目同步路由键
     */
    public static final String SYNC_PLATFORM_CATEGORY_KEY = "sync.platform.category.key";

    /**
     * 流水更新队列
     */
    public static final String WOW_ORDER_WATER_NUMBER_QUEUE = "wow.order.water.number.queue";
    /**
     * 流水更新交换机
     */
    public static final String WOW_ORDER_WATER_NUMBER_EXCHANGE = "wow.order.water.number.exchange";
    /**
     * 流水更新路由键
     */
    public static final String WOW_ORDER_WATER_NUMBER_KEY = "wow.order.water.number.key";

    /**
     * 取消订单队列
     */
    public static final String WOW_CANCEL_ORDER_QUEUE = "wow.cancel.order.queue";
    /**
     * 取消订单交换机
     */
    public static final String WOW_CANCEL_ORDER_EXCHANGE = "wow.cancel.order.exchange";
    /**
     * 取消订单路由键
     */
    public static final String WOW_CANCEL_ORDER_KEY = "wow.cancel.order.key";

    /**
     * 取消订单ttl队列
     */
    public static final String WOW_CANCEL_ORDER_TTL_QUEUE = "wow.cancel.order.ttl.queue";
    /**
     * 取消订单ttl交换机
     */
    public static final String WOW_CANCEL_ORDER_TTL_EXCHANGE = "wow.cancel.order.ttl.exchange";
    /**
     * 取消订单ttl路由键
     */
    public static final String WOW_CANCEL_ORDER_TTL_KEY = "wow.cancel.order.ttl.key";

    /**
     * 微信订单退款队列
     */
    public static final String WX_REFUND_ORDER_QUEUE = "wx.refund.order.queue";
    /**
     * 微信订单退款交换机
     */
    public static final String WX_REFUND_ORDER_EXCHANGE = "wx.refund.order.exchange";
    /**
     * 微信订单退款路由键
     */
    public static final String WX_REFUND_ORDER_KEY = "wx.refund.order.key";


    /**
     * 创建会员中台正向订单队列
     */
    public static final String CM_ORDER_CREATE_QUEUE = "cm.order.create.queue";
    /**
     * 创建会员中台正向订单交换机
     */
    public static final String CM_ORDER_CREATE_EXCHANGE = "cm.order.create.exchange";
    /**
     * 创建会员中台正向订单路由键
     */
    public static final String CM_ORDER_CREATE_KEY = "cm.order.create.key";

    /**
     * 创建会员中台逆向订单队列
     */
    public static final String CM_ORDER_RETURN_CREATE_QUEUE = "cm.order.return.create.queue";
    /**
     * 创建会员中台逆向订单交换机
     */
    public static final String CM_ORDER_RETURN_CREATE_EXCHANGE = "cm.order.return.create.exchange";
    /**
     * 创建会员中台逆向订单路由键
     */
    public static final String CM_ORDER_RETURN_CREATE_KEY = "cm.order.return.create.key";

    /**
     * 取消盲盒订单队列
     */
    public static final String WOW_CANCEL_BOX_ORDER_QUEUE = "wow.cancel.box.order.queue";
    /**
     * 取消盲盒订单交换机
     */
    public static final String WOW_CANCEL_BOX_ORDER_EXCHANGE = "wow.cancel.box.order.exchange";
    /**
     * 取消盲盒订单路由键
     */
    public static final String WOW_CANCEL_BOX_ORDER_KEY = "wow.cancel.box.order.key";

    /**
     * 取消盲盒订单ttl队列
     */
    public static final String WOW_CANCEL_BOX_ORDER_TTL_QUEUE = "wow.cancel.box.order.ttl.queue";
    /**
     * 取消盲盒订单ttl交换机
     */
    public static final String WOW_CANCEL_BOX_ORDER_TTL_EXCHANGE = "wow.cancel.box.order.ttl.exchange";
    /**
     * 取消盲盒订单ttl路由键
     */
    public static final String WOW_CANCEL_BOX_ORDER_TTL_KEY = "wow.cancel.box.order.ttl.key";

    /**
     * 微信盲盒订单退款队列
     */
    public static final String WX_REFUND_BOX_ORDER_QUEUE = "wx.refund.box.order.queue";
    /**
     * 微信盲盒订单退款交换机
     */
    public static final String WX_REFUND_BOX_ORDER_EXCHANGE = "wx.refund.box.order.exchange";
    /**
     * 微信盲盒订单退款路由键
     */
    public static final String WX_REFUND_BOX_ORDER_KEY = "wx.refund.box.order.key";

    /**
     * 微信积分兑换订单退款队列
     */
    public static final String WX_REFUND_POINT_ORDER_QUEUE = "wx.refund.point.order.queue";
    /**
     * 微信积分兑换订单退款交换机
     */
    public static final String WX_REFUND_POINT_ORDER_EXCHANGE = "wx.refund.point.order.exchange";
    /**
     * 微信积分兑换订单退款路由键
     */
    public static final String WX_REFUND_POINT_ORDER_KEY = "wx.refund.point.order.key";

    /**
     * 微信积分兑换订单-退积分-队列
     */
    public static final String REFUND_MEMBER_POINT_QUEUE = "refund.member.point.queue";
    /**
     * 微信积分兑换订单-退积分-交换机
     */
    public static final String REFUND_MEMBER_POINT_EXCHANGE = "refund.member.point.exchange";
    /**
     * 微信积分兑换订单-退积分-路由键
     */
    public static final String REFUND_MEMBER_POINT_KEY = "refund.member.point.key";


    /**
     * 取消积分兑换订单队列
     */
    public static final String WOW_CANCEL_POINT_EXCHANGE_ORDER_QUEUE = "wow.cancel.point.exchange.order.queue";
    /**
     * 取消积分兑换订单交换机
     */
    public static final String WOW_CANCEL_POINT_EXCHANGE_ORDER_EXCHANGE = "wow.cancel.point.exchange.order.exchange";
    /**
     * 取消积分兑换订单路由键
     */
    public static final String WOW_CANCEL_POINT_EXCHANGE_ORDER_KEY = "wow.cancel.point.exchange.order.key";


    /**
     * 取消积分兑换订单ttl队列
     */
    public static final String WOW_CANCEL_POINT_EXCHANGE_ORDER_TTL_QUEUE = "wow.cancel.point.exchange.order.ttl.queue";
    /**
     * 取消积分兑换订单ttl交换机
     */
    public static final String WOW_CANCEL_POINT_EXCHANGE_ORDER_TTL_EXCHANGE = "wow.cancel.point.exchange.order.ttl.exchange";
    /**
     * 取消积分订单ttl路由键
     */
    public static final String WOW_CANCEL_POINT_EXCHANGE_ORDER_TTL_KEY = "wow.cancel.point.exchange.order.ttl.key";


    /**
     * 微信-积分兑换订单-派券-队列
     */
    public static final String POINT_ORDER_BATCH_DISTRIBUTION_QUEUE = "point.order.batch.distribution.queue";
    /**
     * 微信-积分兑换订单-派券-交换机
     */
    public static final String POINT_ORDER_BATCH_DISTRIBUTION_EXCHANGE = "point.order.batch.distribution.exchange";
    /**
     * 微信-积分兑换订单-派券-路由键
     */
    public static final String POINT_ORDER_BATCH_DISTRIBUTION_KEY = "point.order.batch.distribution.key";



    /**
     * 盲盒-创建会员中台正向订单队列
     */
    public static final String CM_BOX_ORDER_CREATE_QUEUE = "cm.box.order.create.queue";
    /**
     * 盲盒-创建会员中台正向订单交换机
     */
    public static final String CM_BOX_ORDER_CREATE_EXCHANGE = "cm.box.order.create.exchange";
    /**
     * 盲盒-创建会员中台正向订单路由键
     */
    public static final String CM_BOX_ORDER_CREATE_KEY = "cm.box.order.create.key";

    /**
     * 盲盒-创建会员中台逆向订单队列
     */
    public static final String CM_BOX_ORDER_REVERSE_QUEUE = "cm.box.order.reverse.queue";
    /**
     * 盲盒-创建会员中台逆向订单交换机
     */
    public static final String CM_BOX_ORDER_REVERSE_EXCHANGE = "cm.box.order.reverse.exchange";
    /**
     * 盲盒-创建会员中台逆向订单路由键
     */
    public static final String CM_BOX_ORDER_REVERSE_KEY = "cm.box.order.reverse.key";


    /**
     * 创建盲盒sku队列
     */
    public static final String MYSTERY_BOX_SKU_CREATE_QUEUE = "mystery.box.sku.create.queue";
    /**
     * 创建盲盒sku交换机
     */
    public static final String MYSTERY_BOX_SKU_CREATE_EXCHANGE = "mystery.box.sku.create.exchange";
    /**
     * 创建盲盒sku路由键
     */
    public static final String MYSTERY_BOX_SKU_CREATE_KEY = "mystery.box.sku.create.key";

    /**
     * 创建中通快递订单队列
     */
    public static final String ZTO_ORDER_CREATE_QUEUE = "zto.order.create.queue";
    /**
     * 创建中通快递订单交换机
     */
    public static final String ZTO_ORDER_CREATE_EXCHANGE = "zto.order.create.exchange";
    /**
     * 创建中通快递订单路由键
     */
    public static final String ZTO_ORDER_CREATE_KEY = "zto.order.create.key";

    /**
     * 创建中通快递订单超时ttl队列
     */
    public static final String ZTO_ORDER_CREATE_TIMEOUT_TTL_QUEUE = "zto.order.create.timeout.ttl.queue";
    /**
     * 创建中通快递订单超时ttl交换机
     */
    public static final String ZTO_ORDER_CREATE_TIMEOUT_TTL_EXCHANGE = "zto.order.create.timeout.ttl.exchange";
    /**
     * 创建中通快递订单超时ttl路由键
     */
    public static final String ZTO_ORDER_CREATE_TIMEOUT_TTL_KEY = "zto.order.create.timeout.ttl.key";


    /**
     * 取消中通快递订单队列
     */
    public static final String ZTO_CANCEL_ORDER_QUEUE = "zto.cancel.order.queue";
    /**
     * 取消中通快递订单交换机
     */
    public static final String ZTO_CANCEL_ORDER_EXCHANGE = "zto.cancel.order.exchange";
    /**
     * 取消中通快递订单路由键
     */
    public static final String ZTO_CANCEL_ORDER_KEY = "zto.cancel.order.key";

    /**
     * 取消中通快递订单ttl队列
     */
    public static final String ZTO_CANCEL_ORDER_TTL_QUEUE = "zto.cancel.order.ttl.queue";
    /**
     * 取消中通快递订单ttl交换机
     */
    public static final String ZTO_CANCEL_ORDER_TTL_EXCHANGE = "zto.cancel.order.ttl.exchange";
    /**
     * 取消中通快递订单ttl路由键
     */
    public static final String ZTO_CANCEL_ORDER_TTL_KEY = "zto.cancel.order.ttl.key";

}
