package com.hins.sp07springsecurity.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author qixuan.chen
 * @date 2019-07-08 21:57
 */
@Data
@ToString
public class SysRole implements Serializable {
    private static final long serialVersionUID = -4346317207051704699L;

    private Long id;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色拥有的权限
     */
    private List<SysPermission> permissionList;

    public SysRole() {
    }

    public SysRole(String roleCode, String roleName) {
        this.roleCode = roleCode;
        this.roleName = roleName;
    }
}
