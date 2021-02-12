//package com.hins.sp18shardingsphere.config;
//
//import com.google.common.collect.Range;
//import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
//import org.springframework.stereotype.Component;
//
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//
///**
// * @author qixuan.chen
// * @date 2021-02-10 21:09
// */
//@Component
//public class TableRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {
//
////    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
//    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
//
//    @Override
//    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Date> rangeShardingValue) {
//        Collection<String> collect = new ArrayList<>();
//        Range<Date> valueRange = rangeShardingValue.getValueRange();
//        //TODO 这种写法只支持between, 但是效率很高
//        String begin = String.valueOf(valueRange.lowerEndpoint()).substring(0, 7).replace("-", "");
//        String end = String.valueOf(valueRange.upperEndpoint()).substring(0, 7).replace("-", "");
//        String between = rangeShardingValue.getLogicTableName() + "_" + begin;
//        String and = rangeShardingValue.getLogicTableName() + "_" + end;
//        for (String each : collection) {
//            if (between.equals(each) || and.equals(each)) {
//                collect.add(each);
//            }
//        }
//        return collect;
//    }
//}