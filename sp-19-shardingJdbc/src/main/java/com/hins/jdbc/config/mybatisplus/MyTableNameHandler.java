package com.hins.jdbc.config.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;

import java.util.Random;

/**
 * @author : chenqixuan
 * @date : 2021/4/13
 */
public class MyTableNameHandler implements TableNameHandler {

    /**
     * @param sql       原始SQL
     * @param tableName 表名
     * @return 动态表名
     */
    @Override
    public String dynamicTableName(String sql, String tableName) {
        // 模拟获取月份参数，实际应该从参数中获取
        //String[] month = {"", "_0", "_1"};
        String[] month = {"_0", "_1"};
        // 随机获取
        int nextInt = new Random().nextInt(2);
        String dynamicTableName = "order_cancel_apply" + month[nextInt];
        System.err.println("动态查询表：" + dynamicTableName);
        return dynamicTableName;
    }


    /**
     * @param sql       原始SQL
     * @param tableName 表名
     * @return 动态表名
     */
//    @Override
//    public String dynamicTableName(String sql, String tableName) {
//        // 模拟获取月份参数，实际应该从参数中获取
//        //String[] month = {"", "_0", "_1"};
//        String[] month = {"_0", "_1"};
//        // 随机获取
//        int nextInt = new Random().nextInt(2);
//        String dynamicTableName = "order_cancel_apply" + month[nextInt];
//        System.err.println("动态查询表：" + dynamicTableName);
//        return dynamicTableName;
//    }
}

