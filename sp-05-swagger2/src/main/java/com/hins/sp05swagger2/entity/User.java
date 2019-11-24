package com.hins.sp05swagger2.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author qixuan.chen
 * @date 2019-07-06 22:30
 */
@Data
public class User {

    private int id;
    private String username;
    private int age;
    private Date ctm;
}
