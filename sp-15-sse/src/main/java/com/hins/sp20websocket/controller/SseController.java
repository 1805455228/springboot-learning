package com.hins.sp20websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Random;

/**
 * @author qixuan.chen
 * @date 2020-03-11 18:54
 */
@Slf4j
@Controller
public class SseController {

    @RequestMapping("index")
    public String home(){

        return "index";
    }

    /**
     * 推送新闻
     * 浏览器无需刷新，即可以收到消息
     * @return
     */
    @RequestMapping("news")
    public String ssetext(){

        return "/sse/news";
    }

    /**
     * 模拟股票实时数据
     * @return
     */
    @RequestMapping("/stock")
    public String sseStock(){

        return "/sse/stock";
    }


    /**
     * 热点推送
     * @return
     */
    @RequestMapping("/sse/news")
    @ResponseBody
    public void sseStockData2(HttpServletResponse response){
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        Random r = new Random();
        int sendCount = 0;
        String array[] = {"新闻1","新闻2","新闻3","新闻4","新闻5",};
        try {
            PrintWriter pw = response.getWriter();

            if(pw.checkError()){
                log.info("客户端断开连接！");
                return;
            }
            Thread.sleep(3000);
            sendCount = r.nextInt(5);
            log.info("新闻:"+sendCount);
            //数据格式必需要这样的格式："data:"+result+"\n\n"
            pw.println("data:"+array[sendCount]+"\n\n");
            pw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
