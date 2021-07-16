package com.hins.sp09redis.common.constant.redis;

/**
 * 门店服务redis key
 *  @author chengwenzhi
 */
public class CoreRedisConstant {

    /**
     * 第一层类目数据缓存:门店id
     */
    public static final String FIRST_PLATFORM_CATEGORY = "FIRST_PLATFORM_CATEGORY:%s";

    /**
     * 其他层类目数据缓存:门店id:上级id
     */
    public static final String PLATFORM_CATEGORY_UPPER = "PLATFORM_CATEGORY_UPPER:%s:%s";

    /**
     * 商品数据缓存
     * %s 门店id
     * %s 类目id
     * %s 价格排序
     * %s 页数
     * %s 条数
     */
    public static final String SKU = "SKU-NEW-1:%s:%s:%s:%s:%s";

    /**
     * 商品数据缓存
     * %s 门店id
     * %s 品牌id
     * %s 页数
     * %s 条数
     */
    public static final String BRAND_SKU = "BRAND-SKU-NEW-1:%s:%s:%s:%s:%s";

    /**
     * 商品数据缓存
     * %s 商品id
     */
    public static final String SKU_ID = "SKU:ID:%s";

    /**
     * 定时获取门店数据锁
     */
    public static final String PLATFORM_STORE_EVERY_DAY = "PLATFORM_STORE_EVERY_DAY:LOCK";

    /**
     * 定时获取spu数据锁
     */
    public static final String PLATFORM_SPU_EVERY_DAY = "PLATFORM_SPU_EVERY_DAY:LOCK";

    /**
     * 定时获取brand数据锁
     */
    public static final String PLATFORM_BRAND_EVERY_DAY = "PLATFORM_BRAND_EVERY_DAY:LOCK";

    /**
     * 定时更新店铺商品信息到opensearch
     */
    public static final String OPENSEARCH_SKU_EVERY_DAY = "OPENSEARCH_SKU_EVERY_DAY:LOCK";

    /**
     * 门店信息缓存
     */
    public static final String STORE_INFORMATION = "store_information:%s";

    /**
     * 活动信息
     */
    public static final String ACTIVITY_CONFIG_INFO = "ACTIVITY:CONFIG:INFO:%s";

    /**
     * 活动商品组件商品列表缓存key
     */
    public static final String ACTIVITY_CONFIG_DETAIL_SKU_MODEL="ACTIVITY_CONFIG_DETAIL:SKU_MODEL:%s:%s:%s:%s";

    /**
     * 妆容兑换活动信息
     */
    public static final String MAKEUP_ACTIVITY_INFO = "MAKEUP_ACTIVITY:INFO:%s";

    /**
     * 妆容兑换活动门店预约时间段
     */
    public static final String MAKEUP_ACTIVITY_RESERVATION_TIME="MAKEUP_ACTIVITY:RESERVATION_TIME:%s:%s";

    /**
     * 妆容兑换活动列表信息
     */
    public static final String MAKEUP_ACTIVITY_INFO_LIST= "MAKEUP_ACTIVITY:INFO_LIST:%s:%s";
}
