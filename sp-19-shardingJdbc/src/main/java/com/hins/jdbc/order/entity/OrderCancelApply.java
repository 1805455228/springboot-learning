package com.hins.jdbc.order.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 订单取消申请表
 * </p>
 *
 * @author chenqixuan
 * @since 2021-04-13
 */
@Data
@Accessors(chain = true)
@ApiModel(value="OrderCancelApply对象", description="订单取消申请表")
public class OrderCancelApply {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "小程序订单id")
    private String orderId;

    @ApiModelProperty(value = "会员id")
    private Long memberId;

    @ApiModelProperty(value = "类型：CancelTypeEnum	(1, 客户未支付取消),	(2,超时未支付系统自动取消),	(3, 客户已支付,20秒内取消),	(4, 客户已支付,后台客服取消),	(5, 海鼎创建订单失败)")
    private Integer cancelType;

    @ApiModelProperty(value = "取消原因")
    private String reason;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
