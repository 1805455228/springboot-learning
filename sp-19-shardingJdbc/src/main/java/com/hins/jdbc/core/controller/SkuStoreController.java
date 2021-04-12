package com.hins.jdbc.core.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hins.jdbc.config.mybatisplus.ValueTableNameParser;
import com.hins.jdbc.core.entity.SkuStore;
import com.hins.jdbc.core.service.ISkuStoreService;
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
            SkuStore entity = new SkuStore();
            entity.setSkuId(UUID.randomUUID().toString().replace("-",""));
            entity.setStoreId(storeId);
            entity.setPrice(new BigDecimal(30.00));
            entity.setMbrPrice(new BigDecimal(80.00));
            entity.setStock(200);
            entity.setSkuStatus(0);
            entity.setUpdateTime(new Date());
            ValueTableNameParser.setThreadLocalValue(storeId);
            skuStoreService.save(entity);
            return "添加成功"+storeId;
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
