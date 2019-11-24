package com.hins.sp05swagger2.entity;

import lombok.Data;

/**
 * @author qixuan.chen
 * @date 2019-07-06 22:31
 */
@Data
public class JsonResult {

    private String status = null;

    private Object result = null;
}
