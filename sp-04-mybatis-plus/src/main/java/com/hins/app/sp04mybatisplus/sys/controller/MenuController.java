package com.hins.app.sp04mybatisplus.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hins.app.sp04mybatisplus.sys.entity.Menu;
import com.hins.app.sp04mybatisplus.sys.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.hins.app.sp04mybatisplus.common.BaseController;

import java.awt.print.Pageable;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-07-06
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController extends BaseController {

    @Autowired
    private IMenuService iMenuService;

    @RequestMapping("/list")
    public IPage<Menu> getData(){
        Page<Menu> p = new Page<>(0, 10);

        IPage page = iMenuService.pageMaps(p);


        return page;
    }

}
