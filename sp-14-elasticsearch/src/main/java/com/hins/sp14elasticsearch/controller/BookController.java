package com.hins.sp14elasticsearch.controller;


import com.hins.sp14elasticsearch.entity.Book;
import com.hins.sp14elasticsearch.entity.EsEntity;
import com.hins.sp14elasticsearch.utils.EsUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author qixuan.chen
 * @date 2019-09-18 22:27
 */
@RestController
@RequestMapping("/book")
public class BookController {


    @Autowired
    private EsUtils esUtil;

    /**
     * @param id 获取某一个
     */
    @GetMapping("/{id}")
    public Book getById(@PathVariable("id") int id) {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(new TermQueryBuilder("id", id));
        List<Book> res = esUtil.search(EsUtils.INDEX_NAME, builder, Book.class);
        if (res.size() > 0) {
            return res.get(0);
        } else {
            return null;
        }
    }

    /**
     * 获取全部
     */
    @GetMapping("/")
    public List<Book> getAll() {
        return esUtil.search(EsUtils.INDEX_NAME, new SearchSourceBuilder(), Book.class);
    }

    /**
     * 根据关键词搜索某用户下的书
     *
     * @param content 关键词
     */
    @GetMapping("/search")
    public List<Book> searchByUserIdAndName(String userId, String content) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.termQuery("userId", userId));
        boolQueryBuilder.must(QueryBuilders.matchQuery("content", content));
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.size(10).query(boolQueryBuilder);
        return esUtil.search(EsUtils.INDEX_NAME, builder, Book.class);

    }

    /**
     * 单个插入
     *
     * @param book book
     */
    @PutMapping("/")
    public void putOne(@RequestBody Book book) {
        EsEntity<Book> entity = new EsEntity<>(book.getId().toString(), book);
        esUtil.insertOrUpdateOne(EsUtils.INDEX_NAME, entity);
    }

    /**
     * 批量插入
     *
     * @param books books
     */
    @PutMapping("/many")
    public void putList(@RequestBody List<Book> books) {
        List<EsEntity> list = new ArrayList<>();
        books.forEach(item -> list.add(new EsEntity<>(item.getId().toString(), item)));
        esUtil.insertBatch(EsUtils.INDEX_NAME, list);
    }

    /**
     * 批量删除
     *
     * @param list list
     */
    @DeleteMapping("/deleteBatch")
    public void deleteBatch(List<Integer> list) {
        esUtil.deleteBatch(EsUtils.INDEX_NAME, list);
    }

    /**
     * delete by query 根据用户id删除数据
     *
     * @param userId userId
     */
    @DeleteMapping("/userId/{userId}")
    public void deleteByUserId(@PathVariable("userId") String userId) {
        esUtil.deleteByQuery(EsUtils.INDEX_NAME, new TermQueryBuilder("userId", userId));
    }


}

