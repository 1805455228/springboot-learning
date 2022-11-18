package com.hins.app.sp04mybatisplus.lock.service;

import com.hins.app.sp04mybatisplus.lock.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-01-25
 */
public interface IProductService extends IService<Product> {


    /**
     * 事务测试
     * @param entity
     * @return
     */
    Map<Boolean,String> addProduct(Product entity);


    /**
     * try catch 手动回滚事务
     * @param entity
     * @return
     */
    Map<Boolean,String> addProductTry(Product entity);

}
