package com.hins.apiencrypt.entity;

import lombok.Data;

import java.util.List;

/**
 * @author : chenqixuan
 * @date : 2021/8/27
 */
@Data
public class AppUser {

    private Long userId;

    private String userName;

    private Integer age;

    private AppCart cart;

    private List<AppCart> cartList;
}
