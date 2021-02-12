package com.hins.sp18shardingsphere.bookUser.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hins.sp18shardingsphere.bookUser.entity.BookUser;
import com.hins.sp18shardingsphere.bookUser.entity.SysUser;
import com.hins.sp18shardingsphere.bookUser.service.IBookUserService;
import com.hins.sp18shardingsphere.bookUser.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 漫画用户 前端控制器
 * </p>
 *
 * @author chenqixuan
 * @since 2021-02-08
 */
@Slf4j
@RestController
@RequestMapping("/bookUser/sysUser")
public class SysUserController {

    @Autowired
    private IBookUserService bookUserService;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * @Description: 批量保存用户
     */
    @GetMapping("/save")
    public Object saveUser() {
        log.info("开始：");
        List<BookUser> list = bookUserService.list();

        List<SysUser> dataList = new ArrayList<>();
        for (BookUser bookUser : list) {
            SysUser sysUser = new SysUser();
            BeanUtils.copyProperties(bookUser,sysUser);
            dataList.add(sysUser);
        }
        log.info("...{}",dataList.size());
        return sysUserService.saveBatch(dataList);
    }


    /**
     * @Description: 获取用户列表 （无分页，性能差，数据量大时有内存溢出）
     */
    @GetMapping("/list")
    public Object listUser(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize) {

        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        IPage<SysUser> page = new Page<>(pageNum,pageSize);
        page = sysUserService.page(page,wrapper);
        return page;
    }


    /**
     * @Description: 模糊查询 （有分页，性能差，数据量大时有内存溢出）
     */
    @GetMapping("/like")
    public Object listLike(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize) {

        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.like("name","12");
        IPage<SysUser> page = new Page<>(pageNum,pageSize);
        page = sysUserService.page(page,wrapper);
        return page;
    }



    /**
     * @Description: 条件查询 （无分页，性能差，数据量大时有内存溢出）
     */
    @GetMapping("/query")
    public IPage<SysUser> listUser(@RequestBody SysUser user,
                           @RequestParam(required = false) Integer pageNum,
                           @RequestParam(required = false) Integer pageSize) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("status","normal");
        IPage<SysUser> page = new Page<>(pageNum,pageSize);
        //List<SysUser> list = sysUserService.list(wrapper);
        page = sysUserService.page(page,wrapper);
        return page;
    }


    /**
     * @Description: 条件查询 （无分页，性能差，数据量大时有内存溢出）
     */
    @GetMapping("/one")
    public Object userByUid(@RequestBody SysUser user) {
        return sysUserService.getById(user.getId());
    }
}
