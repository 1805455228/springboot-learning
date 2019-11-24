package com.hins.sp07springsecurity.service.impl;

import com.hins.sp07springsecurity.entity.SysPermission;
import com.hins.sp07springsecurity.entity.SysRole;
import com.hins.sp07springsecurity.entity.SysUser;
import com.hins.sp07springsecurity.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qixuan.chen
 * @date 2019-07-08 20:04
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService userService;

    /**
     * 授权的时候是对角色授权，而认证的时候应该基于资源，而不是角色，因为资源是不变的，而用户的角色是会变的
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.getUserByName(username);
        if (null == sysUser) {
            throw new UsernameNotFoundException(username);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (SysRole role :  sysUser.getRoleList()) {

            for (SysPermission permission : role.getPermissionList()) {
                authorities.add(new SimpleGrantedAuthority(permission.getCode()));
            }
        }

        return new User(sysUser.getName(), sysUser.getPassword(), authorities);
    }
}
