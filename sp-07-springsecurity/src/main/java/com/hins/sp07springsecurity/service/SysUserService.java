package com.hins.sp07springsecurity.service;

import com.hins.sp07springsecurity.entity.SysUser;

/**
 * @author qixuan.chen
 * @date 2019-07-08 21:37
 */
public interface SysUserService {

    public SysUser getUserByName(String username);
}
