package com.hins.app.sp04mybatisplus.sys.service.impl;

import com.hins.app.sp04mybatisplus.sys.entity.Menu;
import com.hins.app.sp04mybatisplus.sys.mapper.MenuMapper;
import com.hins.app.sp04mybatisplus.sys.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-07-06
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
