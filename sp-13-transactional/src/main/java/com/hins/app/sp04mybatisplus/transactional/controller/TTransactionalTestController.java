package com.hins.app.sp04mybatisplus.transactional.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hins.app.sp04mybatisplus.sys.entity.Menu;
import com.hins.app.sp04mybatisplus.transactional.entity.TTransactionalTest;
import com.hins.app.sp04mybatisplus.transactional.service.ITTransactionalTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.hins.app.sp04mybatisplus.common.BaseController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

/**
 * <p>
 * springbootäº‹åŠ¡æµ‹è¯• 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-08-13
 */
@RestController
@RequestMapping("/transactional/test")
public class TTransactionalTestController extends BaseController {

    @Autowired
    private ITTransactionalTestService itTransactionalTestService;

    @RequestMapping("/add")
    public String addData(){
        TTransactionalTest t = new TTransactionalTest();
        long l = System.currentTimeMillis();
        t.setName("中国china" + l);
        t.setCreateDate(LocalDateTime.now());

        itTransactionalTestService.save(t);
        int i=10/0;
        return "success";
    }

    /**
     * 增加注解方式事务管理
     * @return
     */
    @RequestMapping("/add2")
    @Transactional
    public String addData2(){
        TTransactionalTest t = new TTransactionalTest();
        long l = System.currentTimeMillis();
        t.setName("中国china222" + l);
        t.setCreateDate(LocalDateTime.now());

        itTransactionalTestService.save(t);
        int i=10/0;
        return "success";
    }
}
