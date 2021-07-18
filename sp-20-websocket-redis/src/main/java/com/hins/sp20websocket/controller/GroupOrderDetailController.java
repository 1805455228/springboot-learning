package com.hins.sp20websocket.controller;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hins.sp20websocket.component.WebSocketServer;
import com.sun.xml.internal.ws.spi.db.DatabindingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 拼单详情页 （测试websocket 获取实时数据）
 * @author : chenqixuan
 * @date : 2021/4/26
 */
@Slf4j
@Api(value = "拼单详情相关接口", tags = "拼单详情相关接口")
@Validated
@RestController
@RequestMapping("/groupOrder")
public class GroupOrderDetailController {

    /** 记录当前在线连接数 */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    /** 存放所有在线的客户端 */
    private static Map<String, Object> goodsMap = new ConcurrentHashMap<>();

    @Autowired
    private WebSocketServer webSocketServer;


    @ApiOperation(value = "实时获取数据")
    @GetMapping(value = "/send")
    public Object getDetailPage2(@RequestParam String projectId, @RequestParam String message) {
        Map<String,Object> map = new HashMap<>();

        map.put("onlineNum",onlineCount);
        map.put("message",message);
        map.put("goodsList",goodsMap);
        String msg = JSONObject.toJSONString(map);
        webSocketServer.sendMessageAll(projectId,msg);
        return "success";
    }

    @ApiOperation(value = "获取拼单详情信")
    @GetMapping(value = "/sendOne")
    public Object getDetailPage3(@RequestParam String projectId,@RequestParam String userId, @RequestParam String message) {
        //SpuAndSkuResp spuAndSku = skuService.getSpuAndSku(req.getStoreId(), req.getSpuId(), req.getSkuId());
        webSocketServer.sendMessageById(projectId,userId,message);
        return "success";
    }

    @ApiOperation(value = "通过邀请链接参加拼团")
    @GetMapping(value = "/send")
    public Object add(@RequestParam String projectId, @RequestParam String message) {
        // todo redis 保存 参加人数 + 1
        onlineCount.incrementAndGet();
        return "success";
    }

    @ApiOperation(value = "通过邀请链接退团拼团")
    @GetMapping(value = "/send")
    public Object remove(@RequestParam String projectId, @RequestParam String message) {
        // todo redis 保存 参加人数 - 1
        onlineCount.decrementAndGet();
        return "success";
    }

    @ApiOperation(value = "选购商品")
    @GetMapping(value = "/send")
    public Object addGoods(@RequestParam String projectId, @RequestParam String message) {
        // todo redis 保存  操作商品数据
        List list = Lists.newArrayList();
        for(int i=0;i<=10;i++){
            Map<String,Object> goods = new HashMap<>();
            goods.put("userId", Math.random()*3);
            goods.put("name", "名称"+i);
            goods.put("createTime", new Date());
            list.add(goods);
        }
        goodsMap.put(projectId,list);
        return "success";
    }

}
