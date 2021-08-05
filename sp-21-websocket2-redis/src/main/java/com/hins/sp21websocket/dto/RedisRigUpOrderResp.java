package com.hins.sp21websocket.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 */
@Data
public class RedisRigUpOrderResp implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("拼单唯一标识")
    private String uuid;

    @ApiModelProperty("店铺id")
    private String storeId;

    @ApiModelProperty("拼单状态 1拼单完成 0拼单中")
    private String orderStatus;

    @ApiModelProperty("拼单成员头像")
    private List<String> avatarsList;

    @ApiModelProperty("已点完人数")
    private Integer haveOrderNum;

    @ApiModelProperty("已经支付的人数")
    private Integer havePayNum;

    @ApiModelProperty("正在选购中人数")
    private Integer purchasingNum;

    @ApiModelProperty("拼单开始时间")
    private Date startTime;

    @ApiModelProperty("拼单结束时间")
    private Date endTime;

    @ApiModelProperty("系统当前时间")
    private Date currentTime;

    @ApiModelProperty("支付时间")
    private Date payTime;

    @ApiModelProperty("订单号")
    private String orderId;

    @ApiModelProperty("订单状态")
    private Integer status;

    @ApiModelProperty("下单方式  0-自取 1-外卖")
    private Integer groupWay;

    @ApiModelProperty("拼单类型  1-正常单  2-两人团  3-三人团  4-四人团  0-好友拼单")
    private Integer groupType;

    @ApiModelProperty("拼单文案")
    private String copywriter;


}
