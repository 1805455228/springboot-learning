//package com.hins.sp18shardingsphere.config;
//
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import java.util.Collection;
//
///**
// * @author qixuan.chen
// * @date 2021-02-09 16:49
// */
////@Component
//public class UserAlgorithm implements PreciseShardingAlgorithm<String> {
//
//
//    @Override
//    public String doSharding(Collection<String> tableNames, PreciseShardingValue<String> preciseShardingValue) {
//        if(!StringUtils.isEmpty(preciseShardingValue)){
//            String shardingValue = preciseShardingValue.getValue();
//            int index = getIndex(shardingValue);
//            for(String name : tableNames){
//                if(name.endsWith(index+"")){
//                    return name;
//                }
//            }
//
//        }
//
//        return null;
//    }
//
//
//    private int getIndex(String uid){
//        uid = uid.replace("-","");
//        int res =  Math.abs(uid.hashCode())%10;
//        return res;
//    }
//}
//
//
