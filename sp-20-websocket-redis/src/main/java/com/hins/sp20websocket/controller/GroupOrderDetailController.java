package com.hins.sp20websocket.controller;


import com.hins.sp20websocket.component.WebSocketServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    @Autowired
    private WebSocketServer webSocketServer;


    @ApiOperation(value = "获取拼单详情信息")
    @GetMapping(value = "/send")
    public Object getDetailPage2(@RequestParam String projectId, @RequestParam String message) {
        //SpuAndSkuResp spuAndSku = skuService.getSpuAndSku(req.getStoreId(), req.getSpuId(), req.getSkuId());
        webSocketServer.sendMessageAll(projectId,message);
        return "success";
    }

    @ApiOperation(value = "获取拼单详情信息")
    @GetMapping(value = "/sendOne")
    public Object getDetailPage3(@RequestParam String projectId,@RequestParam String userId, @RequestParam String message) {
        //SpuAndSkuResp spuAndSku = skuService.getSpuAndSku(req.getStoreId(), req.getSpuId(), req.getSkuId());
        webSocketServer.sendMessageById(projectId,userId,message);
        return "success";
    }

}
