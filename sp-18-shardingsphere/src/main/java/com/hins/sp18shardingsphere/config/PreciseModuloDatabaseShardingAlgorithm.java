//package com.hins.sp18shardingsphere.config;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
//
//import java.util.Collection;
//
//
///**
// * @author qixuan.chen
// * @date 2021-02-10 18:39
// */
//public class PreciseModuloDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<String> {
//
//    @Override
//    public String doSharding(Collection<String> collection, PreciseShardingValue<String> prec) {
//        try {
//            //配置的分库分片的sharding-column对应的值,也就是具体时间
//            String str = prec.getValue();
//            if (str.isEmpty()) {
//                throw new UnsupportedOperationException("pre is null");
//            }
//            // each为每个库的名字
//            for (String each : collection) {
//                // 得到具体年,截取字符串要头不要尾
//                String value = StringUtils.substring(str, 0, 4);
//                // 以5年为一个库,例如：2020~2024
//                int c = Integer.parseInt(value) - 2020;
//                // 算差值，拿当前时间减去2020，用差除于5，会得到小数，用int强转，只要整数，这样为0的就放到1库，为1的就放到2库
//                // 0.2（2021年放到1库），1.2（2026年放到2库）
//                int databaseSuffix = c / 5;
//                if (each.endsWith(Integer.toString(databaseSuffix + 1))) {
//                    // 扔到后缀是databaseSuffix+1的库,判断当前这个库是否符合我条件，
//                    // 而不是我去找符合我条件的库，因为没法找，好多库呢
//                    return each;
//                }
//            }
//        } catch (UnsupportedOperationException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}