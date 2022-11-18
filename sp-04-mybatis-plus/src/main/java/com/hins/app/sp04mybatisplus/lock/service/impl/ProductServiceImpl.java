package com.hins.app.sp04mybatisplus.lock.service.impl;

import com.hins.app.sp04mybatisplus.lock.entity.Product;
import com.hins.app.sp04mybatisplus.lock.mapper.ProductMapper;
import com.hins.app.sp04mybatisplus.lock.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-01-25
 */
@Slf4j
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {


    /**
     * 本类方法直接调用，事务无效, 通过接口调用，有效
     * 事务控制记得在SpringBoot的入口类开启事务 @EnableTransactionManagement
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pair<Boolean,String> asyncAddProduct(Product entity){
        String name = entity.getName();

        //异步线程中的事务
        log.info("异步线程事务");
        if("name2".equals(entity.getName())){
            System.out.println("添加异常："+entity.getName());
            this.save(entity);
            int m = 10/0;
            String msg = String.format("添加失败，名称：%s",name);
            return Pair.of(false,msg);
        }else if("name3".equals(entity.getName())){
            System.out.println("添加异常："+entity.getName());
            this.save(entity);
            int m = 10/0;
            String msg = String.format("添加失败，名称：%s",name);
            return Pair.of(false,msg);
        }else if("name4".equals(entity.getName())){
            System.out.println("添加异常："+entity.getName());
            this.save(entity);
            int m = 10/0;
            String msg = String.format("添加失败，名称：%s",name);
            return Pair.of(false,msg);
        } else{
            System.out.println("添加成功："+entity.getName());
            this.save(entity);
            String msg = String.format("添加成功，名称：%s",name);
            return Pair.of(true,msg);
        }

    }

    /**
     * 本类方法直接调用，事务无效, 通过接口调用，有效
     * 事务控制记得在SpringBoot的入口类开启事务 @EnableTransactionManagement
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<Boolean,String> addProduct(Product entity){

        Map<Boolean,String> result = new HashMap<>();
        log.info("异步线程执行");
        if("name2".equals(entity.getName())){
            System.out.println("添加异常："+entity.getName());
            this.save(entity);
            result.put(false,"添加异常");
            int m = 10/0;

        }else{
            System.out.println("添加成功："+entity.getName());
            this.save(entity);
            result.put(true,"添加异常");
        }

        return result;
    }


    /**
     * 本类方法直接调用，事务无效, 通过接口调用，有效
     * 事务控制记得在SpringBoot的入口类开启事务 @EnableTransactionManagement
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<Boolean,String> addProductTry(Product entity){

        Map<Boolean,String> result = new HashMap<>();
        log.info("异步线程执行");
        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        try {
            if("name2".equals(entity.getName())){
                System.out.println("添加异常："+entity.getName());
                this.save(entity);
                result.put(false,"添加异常");
                int m = 10/0;

            }else{
                System.out.println("添加成功："+entity.getName());
                this.save(entity);
                result.put(true,"添加异常");
            }
        } catch (Exception e) {
            log.error("try catch 异步线程异常");
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);

        }

        return result;
    }

}
