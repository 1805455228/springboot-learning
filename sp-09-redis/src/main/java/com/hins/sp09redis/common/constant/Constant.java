package com.hins.sp09redis.common.constant;

import java.text.SimpleDateFormat;

/**
 * 全局常量
 * @author zhangyong
 */
public class Constant {

    public static final String UPDATE = "update";

    public static final String DELETE = "delete";

    public static final String SUCCESS_LOWERCASE = "success";

    public static final String WX_PAY_SUCCESS = "SUCCESS";

    /**
     * 丰桥揽件时间范围
     */
    public static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String startDateStr = "2020-01-01 09:00:00";

    public static final String endDateStr = "2020-01-01 18:00:00";

    public static final String orderStartDateStr = "2020-01-01 00:00:00";

    public static final String orderEndDateStr = "2020-01-01 10:00:00";

    public static final String PREFIX_DATE = "2020-01-01 ";

    public static final String SUFFIX_DATE = " 09:00:00";

    /**
     * 同城送预约时间范围
     */
    public static final String FAST_START_DATE = "2020-01-01 10:00:00";

    public static final String FAST_END_DATE = "2020-01-01 20:00:00";

    public static final String FAST_LAST_DATE = "2020-01-01 23:59:59";

    /**
     * 同城达新预约时间范围
     */
    public static final String FAST_START_DATE_NEW = "09:00:00";

    public static final String FAST_END_DATE_NEW = "22:00:00";

    /**
     * 门店保底营业时间
     */
    public static final String SHOP_OPEN_TIME = "09:00:00";

    public static final String SHOP_CLOSE_TIME = "18:00:00";

    /**
     * 注册渠道：
     * wechatApplet微信小程序
     */
    public static final String REGISTERED_TYPE = "wechatApplet";

    /**
     * 二维码后缀
     * wechatApplet微信小程序
     */
    public static final String EWM = "qr";

    /**
     * id
     */
    public static final String id = "id";

    /**
     * 商品ID
     * skuId
     */
    public static final String skuId = "skuId";

    /**
     * 分享KEY
     */
    public static final String shareKey = "shareKey";
}
