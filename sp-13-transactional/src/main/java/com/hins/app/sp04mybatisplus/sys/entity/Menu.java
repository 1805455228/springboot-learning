package com.hins.app.sp04mybatisplus.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hins.app.sp04mybatisplus.common.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author jobob
 * @since 2019-07-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_menu")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long menuId;

    /**
     * 菜单编号
     */
    private String code;

    /**
     * 菜单父编号
     */
    private String pcode;

    /**
     * 当前菜单的所有父菜单编号
     */
    private String pcodes;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * url地址
     */
    private String url;

    /**
     * 菜单排序号
     */
    private Integer sort;

    /**
     * 菜单层级
     */
    private Integer levels;

    /**
     * 是否是菜单(字典)
     */
    private String menuFlag;

    /**
     * 备注
     */
    private String description;

    /**
     * 菜单状态(字典)
     */
    private String status;

    /**
     * 是否打开新页面的标识(字典)
     */
    private String newPageFlag;

    /**
     * 是否打开(字典)
     */
    private String openFlag;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 修改人
     */
    private Long updateUser;


}
