package com.hins.jdbc.config.shard;

public interface ShardStrategy {
    String getTableName(String var1, Object var2, String var3);
}
