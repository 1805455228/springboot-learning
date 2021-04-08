package com.hins.core.config.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 按参数值分表解析
 * @author : chenqixuan
 * @date : 2021/4/8
 */
public class ValueTableNameParser implements TableNameHandler {

    private String value;

    //使用ThreadLocal防止多线程相互影响
    private static ThreadLocal<String> threadLocalValue = new ThreadLocal<String>();

    public static void setThreadLocalValue(String threadValue) {
        threadLocalValue.set(threadValue);
    }

    @Override
    public String dynamicTableName(String sql, String tableName) {
        String idValue = threadLocalValue.get();
        if (StringUtils.isBlank(idValue)) {
            throw new RuntimeException("请设置id参数值");
        } else {
            String suffix = idValue;
            //这里清除ThreadLocal的值，防止线程复用出现问题
            threadLocalValue.set(null);
            return tableName + "_" + suffix;
        }
    }
}

