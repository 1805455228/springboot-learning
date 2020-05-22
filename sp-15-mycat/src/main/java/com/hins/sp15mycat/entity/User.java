package com.hins.sp15mycat.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenqixuan
 * @version 1.0
 * @date 2020/5/21 11:17
 */
@Entity
@Table(name = "tb_user")
@Data
public class User {

    @Id
    private Long id;

    private String name;

    private Integer gender;

}
