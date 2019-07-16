package com.hins.sp06thymeleaf.entity;

import lombok.Data;

/**
 * @author qixuan.chen
 * @date 2019-07-07 13:36
 */

@Data
public class User {

    private Long id;

    private String name;

    private String password;

    private String role;
}
