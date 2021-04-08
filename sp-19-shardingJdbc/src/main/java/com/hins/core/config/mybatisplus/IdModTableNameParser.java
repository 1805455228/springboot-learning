package com.hins.core.config.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;

/**
 * 按id取模分表处理器
 * @author : chenqixuan
 * @date : 2021/4/8
 */
public class IdModTableNameParser implements TableNameHandler {
    private Integer mod;

    //使用ThreadLocal防止多线程相互影响
    private static ThreadLocal<Integer> id = new ThreadLocal<Integer>();

    public static void setId(Integer idValue) {
        id.set(idValue);
    }

    public IdModTableNameParser(Integer modValue) {
        mod = modValue;
    }

    @Override
    public String dynamicTableName(String sql, String tableName) {
        Integer idValue = id.get();
        if (idValue == null) {
            throw new RuntimeException("请设置id值");
        } else {
            String suffix = String.valueOf(idValue % mod);
            //这里清除ThreadLocal的值，防止线程复用出现问题
            id.set(null);
            return tableName + "_" + suffix;
        }
    }
}

