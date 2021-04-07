package com.hins.core.store.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hins.core.store.entity.SkuStore;
import com.hins.core.store.service.ISkuStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public String getStoreSkuList(@RequestParam String storeId){
        QueryWrapper<SkuStore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",storeId);
        List<SkuStore> skuList = skuStoreService.list(queryWrapper);
        skuList.forEach(item ->{
            log.info("输出结果：{}", JSONUtils.toJSONString(item));
        });

        return "succeess";
    }

    @GetMapping("/add")
    public String addStoreSku(@RequestParam String storeId){

        SkuStore entity = new SkuStore();
        entity.setSkuId(UUID.randomUUID().toString().replace("-",""));
        entity.setStoreId(storeId);
        entity.setPrice(new BigDecimal(30.00));
        entity.setMbrPrice(new BigDecimal(80.00));
        entity.setStock(200);
        entity.setSkuStatus(0);
        entity.setCreateTime(LocalDateTime.now());


        skuStoreService.saveOrUpdate(entity);


        return "succeess";
    }
}
