package com.hins.jdbc.aop;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.hins.jdbc.config.mybatisplus.MyTableNameHandler;
import com.hins.jdbc.utils.ThreadLocalUtils;
import org.apache.shardingsphere.api.hint.HintManager;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : chenqixuan
 * @date : 2021/4/12
 */
@Aspect
@Component
public class ShardingJDBCAop {

    @Pointcut("execution(* com.hins.jdbc.order.mapper.OrderCancelApplyMapper.*(..))")
    public void pc(){}

    @Before("ShardingJDBCAop.pc()")
    public void before(){
//        HintManager hintManager = ThreadLocalUtils.getHintManager();
//        hintManager.addTableShardingValue("order_cancel_apply", ThreadLocalUtils.getIdHintInfo().getMemberId());




    }

    @After("execution(* com.hins.jdbc.order.mapper.OrderCancelApplyMapper.*(..))")
    public void after(){
//        HintManager hintManager = ThreadLocalUtils.getHintManager();
//        hintManager.close();
    }
}

