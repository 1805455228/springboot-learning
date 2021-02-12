//package com.hins.sp18shardingsphere.config;
//
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//
///**
// * @author qixuan.chen
// * @date 2021-02-10 18:40
// */
//public class PreciseModuloTableShardingAlgorithm implements PreciseShardingAlgorithm<String> {
//
//    @Override
//    public String doSharding(Collection<String> collection,
//                             PreciseShardingValue<String> prec) {
//        //对于库的分片collection存放的是所有的库的列表，这里代表master04091,master04092
//        //配置的分片的sharding-column对应的值
//        String timeValue = prec.getValue();
//        System.out.println("===============----------------"+timeValue);
//        //判断timeValue是否为空
//        if (StringUtils.isBlank(timeValue)) {
//            throw new UnsupportedOperationException("prec is null");
//        }
//        // 按年路由，一个库中有5年的数据，在库中我们将每年分成一个表，
//        // 对5取余，判断得到的所有表名后缀是否与取余一致，一致就返回
//        for (String each : collection) {
//            //得到具体年,截取字符串要头不要尾
//            String value = StringUtils.substring(timeValue, 0, 4);
//            System.out.println("===============2----------------"+value);
//            int c = Integer.parseInt(value);
//            //循环每个库，看哪个库与当前条件匹配
//            if (each.endsWith(Integer.toString(c % 3))) {
//                System.out.println("===============2----------------"+each);
//                return each;
//            }
//        }
//        return null;
//    }
//
//
//}
