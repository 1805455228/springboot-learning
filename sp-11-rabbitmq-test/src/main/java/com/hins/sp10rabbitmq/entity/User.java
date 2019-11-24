package com.hins.sp10rabbitmq.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author qixuan.chen
 * @date 2019-08-02 22:27
 */

@Data
@ToString
public class User implements Serializable {

    private String id;

    private String username;

    private String password;

    private String email;

    private String mobile;

    private String nickname;


}
