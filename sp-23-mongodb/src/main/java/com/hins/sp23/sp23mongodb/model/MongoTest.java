package com.hins.sp23.sp23mongodb.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 文档--集合
 * @author : chenqixuan
 * @date : 2021/12/27
 */
@Document(collection = "mongoTest")
public class MongoTest {
    private Integer id;
    private Integer age;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}