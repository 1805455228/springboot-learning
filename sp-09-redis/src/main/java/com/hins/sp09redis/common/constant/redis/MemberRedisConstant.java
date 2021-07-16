package com.hins.sp09redis.common.constant.redis;

/**
 * 用户服务redis key
 *
 * @author zhangyong
 */
public class MemberRedisConstant {

    /**
     * 用户token缓存key
     * member:token:memberId
     */
    public static final String MEMBER_TOKEN = "member:token:%s";

    /**
     * 用户信息缓存key
     * member:info:memberId
     */
    public static final String MEMBER_INFO_KEY = "member:info:%s";

    /**
     * 用户登录缓存锁
     * member:login:lock:jsCode
     */
    public static final String MEMBER_LOGIN_LOCK_KEY = "member:login:lock:%s";

    /**
     * 用户注册缓存锁
     * member:register:lock:openId
     */
    public static final String MEMBER_REGISTER_LOCK_KEY = "member:register:lock:%s";

    /**
     * 用户信息修改缓存锁
     * member:modify:lock:memberId
     */
    public static final String MEMBER_MODIFY_LOCK_KEY = "member:modify:lock:%s";

    /**
     * 会员地址 member_address_info_key:memberAddressId
     */
    public static final String MEMBER_ADDRESS_INFO_KEY = "member_address_info_key:";

    /**
     * 用户活动领券
     */
    public static final String MEMBER_ACTIVITY_COUPON_ACQUIRE_LOCK = "MEMBER:ACTIVITY:COUPON:ACQUIRE:LOCK:%s:%s";

    /**
     * 用户领券数量
     */
    public static final String MEMBER_ACTIVITY_COUPON_ACQUIRE_QUANTITY = "MEMBER:ACTIVITY:COUPON:ACQUIRE:QUANTITY:%s:%s";

    /**
     * 妆容活动已预约人数
     */
    public static final String MAKEUP_ACTIVITY_REDEMPTION_RESERVATION_NUM="MAKEUP_ACTIVITY:REDEMPTION_RESERVATION_NUM:%s:%s:%s:%s:%s";

    /**
     * 妆容活动兑换锁
     */
    public static final String MAKEUP_ACTIVITY_REDEMPTION_LOCK="MAKEUP_ACTIVITY:REDEMPTION:LOCK:%s:%s:%s";

    /**
     * 妆容活动取消预约锁
     */
    public static final String MAKEUP_ACTIVITY_CANCEL_LOCK="MAKEUP_ACTIVITY:CANCEL:LOCK:%s";

    /**
     * 妆容活动签到锁
     */
    public static final String MAKEUP_ACTIVITY_SIGN_IN_LOCK="MAKEUP_ACTIVITY:SIGN_IN:LOCK:%s:%s";

    /**
     * 用户领券总数量
     */
    public static final String MEMBER_ACTIVITY_COUPON_ACQUIRE_MAX_QTY = "MEMBER:ACTIVITY:COUPON:ACQUIRE:MAX:QTY:%s:%s";
}
