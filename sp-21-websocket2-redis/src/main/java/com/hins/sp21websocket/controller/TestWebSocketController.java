package com.hins.sp21websocket.controller;

import com.google.common.collect.Lists;
import com.hins.sp21websocket.api.CommonResult;
import com.hins.sp21websocket.ws.ps.WebSocketPublisher;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : chenqixuan
 * @date : 2021/8/4
 */
@Slf4j
@RestController
@RequestMapping("/ws")
public class TestWebSocketController {

    private final static String WS_TOPIC = "/test/groupSquare/";

    @Autowired
    private WebSocketPublisher publisher;

    @ApiOperation("websocket测试")
    public void getLimitQty(@PathVariable String userId){
        log.info("websocket测试userId：{}",userId);
        //TODO websocket 测试
        String storeId = "A001";
        CommonResult<List<Map<String,Object>>> listCommonResult = groupRenewal(storeId);
        String topic = WS_TOPIC + storeId;
        log.info("补充拼团通知topic={}",topic);
        publisher.sendBroadcastMessage(topic,listCommonResult);

    }


    @ApiOperation("websocket测试")
    @RequestMapping("/test/{userId}")
    public CommonResult<String> getLimitQtyDemo(@PathVariable String userId){
        log.info("controller测试userId：{}",userId);

        return CommonResult.success("ws-controller:"+userId);
    }

    public CommonResult<List<Map<String,Object>>> groupRenewal(String storeId) {
        //websoket通知前端刷新

        //CommonResult<List<RedisRigUpOrderResp>> listCommonResult = groupSquare(storeId, null, squarePageNum, squarePageSize);

        //模拟业务处理
        List<Map<String,Object>> resultList = Lists.newArrayList();
        for (int i= 0; i<=5; i++ ){
            Map<String,Object> map = new HashMap<>();
            map.put("userId","uuu"+i);
            map.put("storeId","sss"+i);
            map.put("name","张三"+i);
            map.put("des","des"+i);
            map.put("age",28);
            resultList.add(map);
        }

        return CommonResult.success(resultList);
    }
}
