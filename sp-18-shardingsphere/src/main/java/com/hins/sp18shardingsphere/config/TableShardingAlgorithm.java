//package com.hins.sp18shardingsphere.config;
//
//import org.springframework.stereotype.Component;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Collection;
//import java.util.Date;
//
///**
// * @author qixuan.chen
// * @date 2021-02-10 21:10
// */
//@Component
//public class TableShardingAlgorithm implements PreciseShardingAlgorithm<Date> {
//
//    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
////    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
//
//    @Override
//    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> preciseShardingValue) {
//        // 基本的表名_年份月份  base_199001
//        String targetTable = preciseShardingValue.getLogicTableName() + "_" + LocalDate.now().format(formatter);
//        if (availableTargetNames.contains(targetTable)) {
//            return targetTable;
//        }
//        throw new UnsupportedOperationException("无效的表名称: " + targetTable);
//    }
//
//}