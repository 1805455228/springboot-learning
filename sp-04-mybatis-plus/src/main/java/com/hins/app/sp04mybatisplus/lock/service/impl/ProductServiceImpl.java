package com.hins.app.sp04mybatisplus.lock.service.impl;

import com.hins.app.sp04mybatisplus.lock.entity.Product;
import com.hins.app.sp04mybatisplus.lock.mapper.ProductMapper;
import com.hins.app.sp04mybatisplus.lock.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-01-25
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
