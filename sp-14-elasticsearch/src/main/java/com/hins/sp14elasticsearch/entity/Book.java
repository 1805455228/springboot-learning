package com.hins.sp14elasticsearch.entity;


import lombok.Data;
import lombok.ToString;

/**
 * @author qixuan.chen
 * @date 2019-09-05 21:40
 */

@Data
@ToString
public class Book {
    //注意 实现的实体类必须指定id属性 不然会报异常
    private Integer id;
    private String userId;
    private String bookName;
    private String author;
    private String content;
    private String publish;
    private Integer price;

}
