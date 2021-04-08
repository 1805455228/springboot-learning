package com.hins.core.store.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * sku库存表
 * </p>
 *
 * @author chenqixuan
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class SkuStore implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * sku id
     */
    private String skuId;

    /**
     * 门店编码
     */
    private String storeId;

    /**
     * 商品状态: 0-下架; 1-上架;
     */
    private Integer skuStatus;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 会员价, 单位元
     */
    private BigDecimal mbrPrice;

    /**
     * 零售价, 单位元
     */
    private BigDecimal price;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
