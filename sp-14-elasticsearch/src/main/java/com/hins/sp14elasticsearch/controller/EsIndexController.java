package com.hins.sp14elasticsearch.controller;


import com.hins.sp14elasticsearch.utils.ElasticsearchUtil;
import com.hins.sp14elasticsearch.utils.EsUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author qixuan.chen
 * @date 2019-09-05 17:07
 */

@RestController
public class EsIndexController {

    @Autowired
    private EsUtils esUtils;

    @Autowired
    private ElasticsearchUtil elasticsearchUtil;



    /**
     * 创建es索引库
     * @param way
     * @param s
     * @return
     */
    @RequestMapping(value = "/index/create/{way}/{indexName}")
    public String createIndex(@PathVariable("way") String way,@PathVariable("indexName") String s){


        if("2".equals(way)){
            esUtils.createIndex(s);
        }else if("3".equals(way)){
            try {
                elasticsearchUtil.createIndex(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("创建es索引库");
        return "success";
    }


    /**
     * 删除es索引库
     * @param way
     * @param indexName
     * @return
     */
    @RequestMapping(value = "/index/del/{way}/{indexName}")
    public String deleteIndex(@PathVariable("way") String way,@PathVariable("indexName") String indexName){
        if("1".equals(way)){

            return "success";
        }else if("2".equals(way)){

        }else if("3".equals(way)){
            try {
                if(StringUtils.isNotBlank(indexName)){
                    elasticsearchUtil.deleteIndex(indexName);
                }
                System.out.println("删除索引库");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "success";
    }







}
