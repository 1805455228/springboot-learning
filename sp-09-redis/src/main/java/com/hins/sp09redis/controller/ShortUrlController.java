package com.hins.sp09redis.controller;


import com.hins.sp09redis.component.RedisUtil;
import com.hins.sp09redis.component.ShortUrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * 短链接跳转
 * @author : chenqixuan
 * @date : 2021/5/17
 */
@RestController
public class ShortUrlController {
    @Autowired
    private RedisUtil redisClient;

    @Autowired
    private ShortUrlUtils shortUrlUtils;

    /**
     * 接收第三方推送过来的数据
     * @return
     */
    @RequestMapping(value = "/shortUrl/{message}")
    public void listParkRecordout(@PathVariable("message") String message, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object obj = redisClient.get(ShortUrlUtils.SHORT_URL + ":" + message);
        if(obj == null) return;

        //重定向
        response.sendRedirect(obj.toString());
    }

    /**
     * 根据连接获取短链接
     */
    @RequestMapping(value = "/add/shortUrl")
    public String LongToShort(String longUrl){
        String[] urls = shortUrlUtils.shortUrl(longUrl);
        Random random = new Random();
        int num = random.nextInt(4);
        String url = urls[num];

        //防止重复
        Object obj = redisClient.get(ShortUrlUtils.SHORT_URL + ":" + url);
        if(obj != null) return LongToShort(longUrl);

        return url;
    }
}

