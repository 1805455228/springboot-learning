package com.hins.jdbc.config.mybatisplus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;


import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
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

import java.util.*;

/**
 * @author qixuan.chen
 * @date 2019-07-06 12:47
 */
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        // 添加动态表名插件 (元数据参数解析已被mybatis-plus3.4版后放弃，应为容易出错，统一使用threadLocal的方式传参数)
//        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor=new DynamicTableNameInnerInterceptor();
//        TableNameHandler tableNameHandler=new MyTableNameHandler();
//        Map<String, TableNameHandler> tableNameHandlerMap=new HashMap<>();
//        tableNameHandlerMap.put("order_cancel_apply",tableNameHandler); // order_tbl表配置动态表名插件
//        dynamicTableNameInnerInterceptor.setTableNameHandlerMap(tableNameHandlerMap);
//        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);

        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

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
