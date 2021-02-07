package com.hins.sp16websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author qixuan.chen
 * @date 2020-03-11 21:18
 */
@RestController
@Slf4j
public class SseDemoController {

    /**
     * 模拟股票实时数据
     * @return
     */
    @RequestMapping(value = "/sse/stock",produces = "text/event-stream;charset=UTF-8")
    public String sseStockData(){
        Random r = new Random();
        int sendCount = 0;

        try {
            Thread.sleep(3000);
            sendCount = r.nextInt(100);
            log.info("行情:"+sendCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = String.valueOf(sendCount);
        //数据格式必需要这样的格式："data:"+result+"\n\n"
        return "data:"+result+"\n\n";
    }
}
