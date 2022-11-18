package com.hins.app.sp04mybatisplus.lock.service;

import com.hins.app.sp04mybatisplus.lock.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-01-25
 */
public interface IProductService extends IService<Product> {




    Pair<Boolean,String> asyncAddProduct(Product entity);

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
