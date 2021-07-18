package com.hins.sp09redis.common.constant.redis;

/**
 * 常用全局redis key
 * @author zhangyong
 */
public class RedisConstant {

    /**
     *
     */
    public static final String LOCK_SUFFIX = ":LOCK";

    /**
     * 海鼎促销报错
     */
    public static final String HD_PROMOTION_EXCEPTION_CACHE_KEY = "WOWCOLOUR_HD_PROMOTION_EXCEPTION";
    /**
     * 海鼎库存报错
     */
    public static String HD_STOCK_EXCEPTION_CACHE_KEY = "WOWCOLOUR_HD_STOCK_EXCEPTION";
    /**
     * 海鼎下单报错
     */
    public static final String HD_ORDER_EXCEPTION_CACHE_KEY = "WOWCOLOUR_HD_ORDER_EXCEPTION";
    /**
     * 运单报错
     */
    public static final String SF_EXCEPTION_CACHE_KEY = "WOWCOLOUR_SF_EXCEPTION";
    /**
     * 异常信息缓存
     */
    public static final String EXCEPTION_INFO = "WOWCOLOUR_EXCEPTION_INFO";
    /**
     * 待发送的邮件内容缓存
     */
    public static final String WAIT_SEND_EMAIL_SMG_LIST = "WOWCOLOUR_WAIT_SEND_EMAIL_SMG_LIST";

    /**
     * 异常告警分布式锁
     */
    public static final String WOWCOLOUR_ALARM_DISTRIBUTED_LOCK = "WOWCOLOUR_ALARM_DISTRIBUTED_LOCK";

    /**
     * shareKey缓存
     */
    public static final String WOWCOLOUR_SHARE_KEY = "WOWCOLOUR_SHARE_KEY";

    /**
     * trackId缓存
     */
    public static final String WOWCOLOUR_TRACK_Id= "WOWCOLOUR_TRACK_Id";


    /**
     * 缓存首页
     */
    public static final String WOWCOLOUR_INDEX= "WOWCOLOUR_INDEX";


    /**
     * 缓存二维码
     */
    public static final String WOWCOLOUR_QR_CODE= "WOWCOLOUR_QR_CODE";
}
