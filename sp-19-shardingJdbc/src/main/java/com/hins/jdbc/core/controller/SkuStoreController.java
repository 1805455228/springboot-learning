package com.hins.jdbc.core.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hins.jdbc.config.mybatisplus.ValueTableNameParser;
import com.hins.jdbc.core.entity.SkuStore;
import com.hins.jdbc.core.service.ISkuStoreService;
import com.hins.jdbc.order.entity.OrderItem;
import com.hins.jdbc.order.service.IOrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * sku库存表 前端控制器
 * </p>
 *
 * @author chenqixuan
 * @since 2021-04-06
 */
@Slf4j
@RestController
@RequestMapping("/store/skuStore")
public class SkuStoreController {

    @Autowired
    private ISkuStoreService skuStoreService;

    @Autowired
    private IOrderItemService orderItemService;

    @GetMapping("/list")
    public Object getStoreSkuList(@RequestParam String storeId){
        try {
//        QueryWrapper<SkuStore> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("store_id",storeId);
//        LambdaQueryWrapper<SkuStore> lambdaQuery = Wrappers.<SkuStore>lambdaQuery();
//        lambdaQuery.eq(SkuStore::getStoreId, storeId);
//
//        ValueTableNameParser.setThreadLocalValue(storeId);
//        List<SkuStore> skuList = skuStoreService.list(lambdaQuery);
//        skuList.forEach(item ->{
//            log.info("输出结果：{}", item.toString());
//        });

            LambdaQueryWrapper<SkuStore> lambdaQuery2 = Wrappers.<SkuStore>lambdaQuery();
            lambdaQuery2.eq(SkuStore::getStoreId, storeId);
            IPage<SkuStore> page = new Page<>(1,3);
            ValueTableNameParser.setThreadLocalValue(storeId);
            page = skuStoreService.page(page,lambdaQuery2);


//        ValueTableNameParser.setThreadLocalValue(storeId);
//        List<SkuStore> skuList2 = skuStoreService.getListByMap(storeId);
//        skuList2.forEach(item ->{
//            log.info("输出结果2：{}", item.toString());
//        });

            Map<String,Object> result = new HashMap<>();
            result.put("skuData",page);
//        result.put("skuList",skuList);
//        result.put("skuList2",skuList2);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "查询异常，表不存在"+storeId;
    }

    @GetMapping("/add")
    public String addStoreSku(@RequestParam String storeId){

        try {
            SkuStore entity2 = new SkuStore();
            entity2.setSkuId(UUID.randomUUID().toString().replace("-",""));
            entity2.setStoreId(storeId);
            entity2.setPrice(new BigDecimal(30.00));
            entity2.setMbrPrice(new BigDecimal(80.00));
            entity2.setStock(200);
            entity2.setSkuStatus(0);
            entity2.setUpdateTime(new Date());
            ValueTableNameParser.setThreadLocalValue(storeId);
            skuStoreService.save(entity2);



            //另外一个数据源的操作

            Integer num = (int)(1+Math.random()*(1000-1+1));
            OrderItem entity = new OrderItem();
            Long time = System.currentTimeMillis();
            entity.setOrderId(String.valueOf(time));
            entity.setMemberId(num.longValue());
            entity.setSpuName("spu商品名称-"+num);
            entity.setSpuId(UUID.randomUUID().toString().replace("-",""));
            entity.setSkuId(UUID.randomUUID().toString().replace("-",""));
            entity.setSkuName("sku商品名称-"+num);
            entity.setQuantity(10);
            entity.setRealAmount(new BigDecimal(30.00));
            entity.setPrice(new BigDecimal(30.00));
            entity.setPromotionAmount(new BigDecimal(10.00));
            entity.setCouponAmount(new BigDecimal(20.00));
            entity.setBarCode(String.valueOf(time));

            entity.setCouponCode(String.valueOf(time));
            entity.setFirstCategoryId(String.valueOf(time));
            entity.setStateDescribe("nnnnnnn-des");
            entity.setUpdateTime(new Date());

            orderItemService.save(entity);
            return "添加成功";
        } catch (Exception e) {
            e.printStackTrace();
        }




        return "添加异常，表不存在"+storeId;
    }


    @GetMapping("/initTable")
    public String initStoreSkuTable(@RequestParam List<String> storeIds){

        skuStoreService.initTables(storeIds);


        return "succeess";
    }
}
