package com.hins.sp07springsecurity.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author qixuan.chen
 * @date 2019-07-07 13:36
 */

@Data
@ToString
public class SysUser implements Serializable {

    private static final long serialVersionUID = -1528008463380636535L;
    private Long id;

    private String name;

    private String password;

    private String role;

    /**
     * 用户角色
     */
    private List<SysRole> roleList;

    public SysUser() {
    }

    public SysUser(String username, String password) {
        this.name = username;
        this.password = password;
    }
}
