package com.hins.jdbc.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * sku库存表
 * </p>
 *
 * @author chenqixuan
 * @since 2021-04-12
 */
@Data
@Accessors(chain = true)
@ApiModel(value="SkuStore对象", description="sku库存表")
public class SkuStore {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "sku id")
    private String skuId;

    @ApiModelProperty(value = "门店编码")
    private String storeId;

    @ApiModelProperty(value = "商品状态: 0-下架; 1-上架;")
    private Integer skuStatus;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "会员价, 单位元")
    private BigDecimal mbrPrice;

    @ApiModelProperty(value = "零售价, 单位元")
    private BigDecimal price;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
