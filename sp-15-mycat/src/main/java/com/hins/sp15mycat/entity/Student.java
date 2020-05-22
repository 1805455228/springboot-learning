package com.hins.sp15mycat.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenqixuan
 * @version 1.0
 * @date 2020/5/21 11:34
 */
@Entity
@Table(name = "tb_student")
@Data
public class Student {

    @Id
    private Long id;

    private String name;

    @Column(unique = true)
    private Long userId;

}
