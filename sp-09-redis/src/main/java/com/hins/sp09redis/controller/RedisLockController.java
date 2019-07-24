package com.hins.sp09redis.controller;


import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * redis 分布式锁
 * @author qixuan.chen
 * @date 2019-07-23 19:19
 */
@RestController
public class RedisLockController {

    private final Logger logger = LoggerFactory.getLogger(RedisLockController.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/redisson")
    public String redisLockRedisson(){

        String lockKey = "lock";
        String uuid = UUID.randomUUID().toString();

        RLock lock = redissonClient.getLock(lockKey);

        try {
            //加锁
            lock.tryLock(10,TimeUnit.SECONDS);
            String key = "goods_num";
            Integer num = Integer.parseInt(stringRedisTemplate.opsForValue().get(key));

            if(num > 0){
                Integer result = num-1;
                stringRedisTemplate.opsForValue().set(key,result + "");
                logger.info("扣减成功，库存剩余：" + result);
            }else{
                logger.info("扣减失败：库存不足");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //释放锁
            lock.unlock();
        }

        return "SUCCESS";
    }


    @GetMapping("/lock1")
    public String redisLock(){

        String lockKey = "lock";
        String uuid = UUID.randomUUID().toString();

        try {
            //锁
            /* Boolean lockStr = stringRedisTemplate.opsForValue().setIfAbsent(lockKey,"cqx");
            stringRedisTemplate.expire(lockKey,10, TimeUnit.SECONDS);*/

            //加锁
            Boolean lockStr = stringRedisTemplate.opsForValue().setIfAbsent(lockKey,uuid,10,TimeUnit.SECONDS);
            if(!lockStr) {//没获取到锁
                return "error";
            }

            String key = "goods_num";
            Integer num = Integer.parseInt(stringRedisTemplate.opsForValue().get(key));

            if(num > 0){
                Integer result = num-1;
                stringRedisTemplate.opsForValue().set(key,result + "");
                logger.info("扣减成功，库存剩余：" + result);
            }else{
                logger.info("扣减失败：库存不足");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //释放锁
            //stringRedisTemplate.delete(lockKey);
            if(uuid.equals(stringRedisTemplate.opsForValue().get(lockKey))){
                stringRedisTemplate.delete(lockKey);
            }
        }

        return "SUCCESS";
    }






}
