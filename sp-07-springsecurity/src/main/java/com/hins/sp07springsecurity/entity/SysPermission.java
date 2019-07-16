package com.hins.sp07springsecurity.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author qixuan.chen
 * @date 2019-07-11 21:27
 */
@Data
@ToString
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 8899099360639201568L;
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限字符串
     */
    private String code;

    /**
     * 资源类型
     */
    private Integer type;

    /**
     * URL
     */
    private String url;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 父菜单ID
     */
    private Long pid;

}
