package com.hins.sp05swagger2.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author qixuan.chen
 * @date 2019-07-06 23:10
 */

@Data
public class Message {
    private Long id;
    @ApiModelProperty(value = "消息体")
    private String text;
    @ApiModelProperty(value = "消息总结")
    private String summary;
    private Date createDate;
}
