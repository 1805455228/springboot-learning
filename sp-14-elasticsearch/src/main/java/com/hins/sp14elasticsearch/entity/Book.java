package com.hins.sp14elasticsearch.entity;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author qixuan.chen
 * @date 2019-09-05 21:40
 */
@Document(indexName = "book",type = "book", shards = 1,replicas = 0, refreshInterval = "-1")
public class Book {
    //注意 实现的实体类必须指定id属性 不然会报异常
    private Integer id;
    private String bookName;
    private String author;
    private String publish;
    private Integer price;


    //  setXXX()和getXXX()

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", publish='" + publish + '\'' +
                ", price=" + price +
                '}';
    }

}
