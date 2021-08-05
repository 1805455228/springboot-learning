package com.hins.sp21websocket.ws.util;

import com.alibaba.fastjson.JSONObject;
import com.hins.sp21websocket.api.CommonResult;
import com.hins.sp21websocket.ws.entity.WsUserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 * @author mpg
 * @since 2021/6/28
 */
@Slf4j
@Controller
public class ReceiveHandler {

    @MessageMapping("/name")
    public void stompHandle(WsUserSession wsUserSession, Message message) {
        log.info("payload" + WsUtil.getPayload(message));
        log.info("接收到消息：{}", JSONObject.toJSONString(wsUserSession));
    }

    @SubscribeMapping("/ping")
    public CommonResult<String> stompHandle() {
        return CommonResult.success("pong");
    }

}
