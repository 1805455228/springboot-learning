package com.hins.sp21websocket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tangyr
 */
@Data
public class CenterMemberInfoResp implements Serializable {
    private int code;
    private String message;
    private MemberBaseResp data;

    @Data
    public static class MemberBaseResp implements Serializable {
        /**
         * 会员编号
         */
        private Long memberId;
        /**
         * 会员姓名
         */
        private String name;
        /**
         * 会员昵称
         */
        private String nickname;

        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
        private Date birthday;

        @ApiModelProperty(value = "性别(0:未知 1:男 2:女)")
        private Integer gender;
        /**
         * 会员头像
         */
        private String coverUrl;
        /**
         * 会员等级类型
         */
        private Integer memberType;
        /**
         * 会员手机号
         */
        private String phone;
        /**
         * 积分余额
         */
        private BigDecimal point;
        /**
         * 待发放冻结积分
         */
        private BigDecimal lockPoint;
        /**
         * 会员成长值
         */
        private Integer growValue;
        /**
         * 微信openId
         */
        private String openId;
        /**
         * 是否被拉黑 0否 1是
         */
        private Integer isLock;
        /**
         * 租户编号
         */
        private String tenantCode;
        /**
         * 最后登录时间
         */
        private Date lastLoginTime;

        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
        private Date registerTime;
        /**
         * 会员等级名称
         */
        private String levelName;
        /**
         * 是否注册0 游客 1有效用户
         */
        private Integer registered;
        /**
         * 会员卡号: 与会员编码一 一对应
         */
        private String memberCard;
        /**
         * 地区
         */
        private String region;
    }
}
