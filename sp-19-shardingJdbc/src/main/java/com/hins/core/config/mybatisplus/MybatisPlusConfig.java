package com.hins.core.config.mybatisplus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;


import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.google.common.collect.Lists;
import org.apache.ibatis.javassist.tools.reflect.Metaobject;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author qixuan.chen
 * @date 2019-07-06 12:47
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.hins.core.*.mapper*")
public class MybatisPlusConfig {

//    /**
//     * 分页插件
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor page = new PaginationInterceptor();
//        page.setCountSqlParser(new JsqlParserCountOptimize(true));
//        return page;
//    }

//    /**
//     * 分页插件 和 按年动态表名
//     * @return
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
//        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
//        dynamicTableNameParser.setTableNameHandlerMap(new HashMap<>(2) {{
//            put("user_info", (metaObject, sql, tableName) -> {
//                // 获取传入参数 year，如果有的话做为后缀，没有的话则使用当前年份作为后缀
//                Object param = getParamValue("year", metaObject);
//                String year = param !=null ? String.valueOf(param)
//                        : String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
//                return tableName + "_" + year;
//            });
//        }});
//        paginationInterceptor.setSqlParserList(Collections.singletonList(dynamicTableNameParser));
//        return paginationInterceptor;
//    }

    /**
     * 分页插件 和 动态表名
     * @return
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
//        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
//        dynamicTableNameParser.setTableNameHandlerMap(new HashMap<String, ITableNameHandler>(2){{
//            //涉及表集合
//            List<String> tables = Lists.newArrayList();
//            tables.add("sku_store");
//            //动态表规则 初始表名+_+code
//            tables.forEach(tableTitle -> put(tableTitle,(metaObject, sql, tableName) -> tableName + "_" + String.valueOf(getParamValue("storeId",metaObject))));
//        }});
//        paginationInterceptor.setSqlParserList(Collections.singletonList(dynamicTableNameParser));
//        return paginationInterceptor;
//    }



    /**
     * 获取参数值
     */
//    private Object getParamValue(String title, MetaObject metaObject){
//        //获取参数
//        Object originalObject = metaObject.getOriginalObject();
//        JSONObject originalObjectJSON = JSON.parseObject(JSON.toJSONString(originalObject));
//        JSONObject boundSql = originalObjectJSON.getJSONObject("boundSql");
//        try {
//            JSONObject parameterObject = boundSql.getJSONObject("parameterObject");
//            return parameterObject.get(title);
//        }catch (Exception e) {
//            return null;
//        }
//    }

    /**
     * SQL执行分析日志
     * @return
     */
//    @Bean
//    //@Profile({"dev", "test", "prod"})
//    @Profile({"dev"})
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor pi = new PerformanceInterceptor();
//        pi.setFormat(true);
//        return pi;
//    }

}
