package com.hins.jdbc.config.shard;//package com.hins.sp18shardingsphere.config;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author qixuan.chen
 * 强制路由只有特殊情况才会使用，必须在调用数据库前使用HintManager将database和table的逻辑表设置，
 * 如果不设置，sharding-jdbc会遍历执行所有路由，一旦设置HintManager，datasource和table都需要设
 * 置对应的路由算法{{@link #doSharding(Collection, HintShardingValue)}}来决定是哪个路由
 * @date 2021-02-10 19:11
 */
public class HintTableShardingAlgorithm implements HintShardingAlgorithm<String> {

//    @Override
//    public Collection<String> doSharding(Collection collection, HintShardingValue hint) {
//        System.out.println(collection);
//        System.out.println(hint);
//        // actualTable中包含sharding-jdbc实际会查询的表
//        return Lists.newArrayList("tb_sys_user1");
//    }


    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<String> hintShardingValue) {
        //这里collection根据我测试的经验它代表的实际节点的集合，hintShardingValue则是分片键
        Collection<String> result = new ArrayList<>();
        for (Object each : availableTargetNames) {
            for (Object value : hintShardingValue.getValues()) {
                String s = each.toString();
                Long aLong = Long.valueOf(String.valueOf(value));
                if (s.endsWith(String.valueOf(aLong % 2))) {
                    result.add(s);
                }
            }
        }
        return result;

    }

}