package com.hins.app.sp04mybatisplus.transactional.service.impl;

import com.hins.app.sp04mybatisplus.transactional.entity.TTransactionalTest;
import com.hins.app.sp04mybatisplus.transactional.mapper.TTransactionalTestMapper;
import com.hins.app.sp04mybatisplus.transactional.service.ITTransactionalTestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * springbootäº‹åŠ¡æµ‹è¯• 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-08-13
 */
@Service
public class TTransactionalTestServiceImpl extends ServiceImpl<TTransactionalTestMapper, TTransactionalTest> implements ITTransactionalTestService {

}
