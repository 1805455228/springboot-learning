package com.hins.jdbc.order.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单商品表
 * </p>
 *
 * @author chenqixuan
 * @since 2021-04-12
 */
@Data
@Accessors(chain = true)
@ApiModel(value="OrderItem对象", description="订单商品表")
public class OrderItem  {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "小程序订单id")
    private String orderId;

    @ApiModelProperty(value = "spu编号")
    private String spuId;

    @ApiModelProperty(value = "spu名称")
    private String spuName;

    @ApiModelProperty(value = "sku编号")
    private String skuId;

    @ApiModelProperty(value = "sku名称")
    private String skuName;

    @ApiModelProperty(value = "sku主图")
    private String skuPic;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal price;

    @ApiModelProperty(value = "真实支付金额")
    private BigDecimal realAmount;

    @ApiModelProperty(value = "优惠券码")
    private String couponCode;

    @ApiModelProperty(value = "优惠券抵扣金额")
    private BigDecimal couponAmount;

    @ApiModelProperty(value = "促销优惠金额")
    private BigDecimal promotionAmount;

    @ApiModelProperty(value = "购买数量")
    private Integer quantity;

    @ApiModelProperty(value = "重量")
    private BigDecimal weight;

    @ApiModelProperty(value = "商品一级分类id")
    private String firstCategoryId;

    @ApiModelProperty(value = "商品一级分类名称")
    private String firstCategoryName;

    @ApiModelProperty(value = "商品二级分类id")
    private String secondCategoryId;

    @ApiModelProperty(value = "商品二级分类名称")
    private String secondCategoryName;

    @ApiModelProperty(value = "商品条码")
    private String barCode;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "会员id")
    private Long memberId;

    @ApiModelProperty(value = "订单明细规格描述")
    private String stateDescribe;


}
