package com.hins.core.order.controller;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hins.core.config.mybatisplus.ValueTableNameParser;
import com.hins.core.order.entity.OrderItem;
import com.hins.core.order.service.IOrderItemService;
import com.hins.core.store.entity.SkuStore;
import com.hins.core.store.service.ISkuStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 订单商品表 前端控制器
 * </p>
 *
 * @author chenqixuan
 * @since 2021-04-12
 */
@RestController
@RequestMapping("/order/orderItem")
@DS("orderDataSource")
public class OrderItemController {


        @Autowired
        private IOrderItemService orderItemService;

        @GetMapping("/list")
        public Object getStoreSkuList(){
            try {

                LambdaQueryWrapper<OrderItem> lambdaQuery2 = Wrappers.<OrderItem>lambdaQuery();
                IPage<OrderItem> page = new Page<>(1,10);
                page = orderItemService.page(page,lambdaQuery2);

                Map<String,Object> result = new HashMap<>();
                result.put("dataList",page);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "查询异常，表不存在";
        }

        @GetMapping("/add")
        public String addStoreSku(){

            Integer num = (int)(1+Math.random()*(1000-1+1));

            try {
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

                orderItemService.save(entity);
                return "添加成功";
            } catch (Exception e) {
                e.printStackTrace();
            }


            return "添加异常，表不存在";
        }
}
