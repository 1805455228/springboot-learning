package com.hins.sp18shardingsphere.bookUser.service.impl;

import com.hins.sp18shardingsphere.bookUser.entity.BookUser;
import com.hins.sp18shardingsphere.bookUser.mapper.BookUserMapper;
import com.hins.sp18shardingsphere.bookUser.service.IBookUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 漫画用户 服务实现类
 * </p>
 *
 * @author chenqixuan
 * @since 2021-02-08
 */
@Service
public class BookUserServiceImpl extends ServiceImpl<BookUserMapper, BookUser> implements IBookUserService {

}
