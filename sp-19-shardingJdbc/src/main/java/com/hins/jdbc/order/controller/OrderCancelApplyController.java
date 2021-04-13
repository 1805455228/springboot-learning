package com.hins.jdbc.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hins.jdbc.core.entity.SkuStore;
import com.hins.jdbc.order.entity.OrderCancelApply;
import com.hins.jdbc.order.entity.OrderItem;
import com.hins.jdbc.order.service.IOrderCancelApplyService;
import com.hins.jdbc.order.service.IOrderItemService;
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
 * 订单取消申请表 前端控制器
 * </p>
 *
 * @author chenqixuan
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/order/orderCancel")
public class OrderCancelApplyController {

    @Autowired
    private IOrderCancelApplyService orderCancelApplyService;

    @GetMapping("/list")
    public Object getList(@RequestParam String memberId){
        try {

            LambdaQueryWrapper<OrderCancelApply> lambdaQuery2 = Wrappers.<OrderCancelApply>lambdaQuery();
            lambdaQuery2.eq(OrderCancelApply::getMemberId,memberId);
            IPage<OrderCancelApply> page = new Page<>(1,5);
            page = orderCancelApplyService.page(page,lambdaQuery2);

            Map<String,Object> result = new HashMap<>();
            result.put("dataList",page);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "查询异常，表不存在";
    }


    @GetMapping("/add")
    public String addData(){

        Integer num = (int)(1+Math.random()*(1000-1+1));

        try {
            OrderCancelApply entity = new OrderCancelApply();
            Long time = System.currentTimeMillis();
            entity.setOrderId(String.valueOf(time));
            entity.setMemberId(num.longValue());
            entity.setCancelType(1);
            entity.setReason("yuan yin reason");
            entity.setUpdateTime(new Date());

            orderCancelApplyService.save(entity);
            return "添加成功";
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "添加异常，表不存在";
    }

}
