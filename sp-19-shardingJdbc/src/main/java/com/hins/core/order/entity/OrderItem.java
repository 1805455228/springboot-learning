package com.hins.core.order.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class OrderItem {

    private static final long serialVersionUID = 1L;

    /**
     * 小程序订单id
     */
    private String orderId;

    /**
     * spu编号
     */
    private String spuId;

    /**
     * spu名称
     */
    private String spuName;

    /**
     * sku编号
     */
    private String skuId;

    /**
     * sku名称
     */
    private String skuName;

    /**
     * sku主图
     */
    private String skuPic;

    /**
     * 销售价格
     */
    private BigDecimal price;

    /**
     * 真实支付金额
     */
    private BigDecimal realAmount;

    /**
     * 优惠券码
     */
    private String couponCode;

    /**
     * 优惠券抵扣金额
     */
    private BigDecimal couponAmount;

    /**
     * 促销优惠金额
     */
    private BigDecimal promotionAmount;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 商品一级分类id
     */
    private String firstCategoryId;

    /**
     * 商品一级分类名称
     */
    private String firstCategoryName;

    /**
     * 商品二级分类id
     */
    private String secondCategoryId;

    /**
     * 商品二级分类名称
     */
    private String secondCategoryName;

    /**
     * 商品条码
     */
    private String barCode;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 订单明细规格描述
     */
    private String stateDescribe;


}
