package com.hins.sp09redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author qixuan.chen
 * @date 2019-07-23 18:13
 */
@RestController
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/hi")
    public String getTest(@PathVariable(name="key") String key){

        return "sssss";
    }

    @GetMapping("/get/{key}")
    public String getRedis(@PathVariable(name="key") String key){

        return stringRedisTemplate.opsForValue().get(key);
    }

    @PostMapping("/set/{key}/{value}")
    public String getRedis(@PathVariable(name="key")String key,@PathVariable(name="value")String value){
        stringRedisTemplate.opsForValue().set(key,value);
        return "SUCCESS";
    }


    @GetMapping("/postEntity")
    public String postEntity(){
        Map<String,Object> user=new ConcurrentHashMap<>();
        user.put("id","1");
        user.put("name","pwl");
        user.put("age","25");
        redisTemplate.opsForValue().set(user.get("id"),user);
        redisTemplate.opsForValue().set(user.get("name"),user);
        return "SUCCESS";
    }

    @GetMapping("/getEntity/{key}")
    public Object getEntity(@PathVariable(name="key")String key){
        return redisTemplate.opsForValue().get(key);
    }
}
