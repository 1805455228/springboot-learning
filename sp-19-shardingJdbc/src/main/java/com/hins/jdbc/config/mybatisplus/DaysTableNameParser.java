package com.hins.jdbc.config.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 按天分表解析
 * @author : chenqixuan
 * @date : 2021/4/8
 */
public class DaysTableNameParser implements TableNameHandler {

    @Override
    public String dynamicTableName(String sql, String tableName) {
        String dateDay = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return tableName + "_" + dateDay;
    }
}

