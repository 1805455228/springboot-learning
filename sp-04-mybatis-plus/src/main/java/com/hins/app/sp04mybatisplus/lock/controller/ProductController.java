package com.hins.app.sp04mybatisplus.lock.controller;


import com.hins.app.sp04mybatisplus.common.BaseController;
import com.hins.app.sp04mybatisplus.lock.entity.Product;
import com.hins.app.sp04mybatisplus.lock.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author nima
 * @since 2021-01-25
 */
@Slf4j
@RestController
@RequestMapping("/lock/product")
public class ProductController extends BaseController {

    @Autowired
    private IProductService productService;

    @RequestMapping("/list")
    public String  testConcurrentUpdate() {
        //1、小李
        Long id = 1L;
        Product p1 = productService.getById(id);
        System.out.println(p1.getPrice());
        //2、小王
        Product p2 = productService.getById(id);
        System.out.println(p2.getPrice());
        //3、小李将价格加了50元，存入了数据库
        p1.setPrice(p1.getPrice() + 50);
        productService.updateById(p1);
        //4、小王将商品减了30元，存入了数据库
        p2.setPrice(p2.getPrice() - 30);
        boolean result = productService.updateById(p2);
        if (!result) {//更新失败，重试
            //重新获取数据
            p2 = productService.getById(id);
            //更新
            p2.setPrice(p2.getPrice() - 30);
            productService.updateById(p2);
        }
        //最后的结果
        Product p3 = productService.getById(id);
        System.out.println("最后的结果：" + p3.getPrice());

        return  p3.getPrice().toString();
    }


    /**
     * Spring Transactional一直是RD的事务神器，但是如果用不好，反会伤了自己。下面总结@Transactional经常遇到的几个场景:
     * @Transactional 加于private方法, 无效
     * @Transactional 加于未加入接口的public方法, 再通过普通接口方法调用, 无效
     * @Transactional 加于接口方法, 无论下面调用的是private或public方法, 都有效（timeout放在下面调用的private或public方法上都不生效，只在外层方法中有效）
     * @Transactional 加于接口方法后, 被本类普通接口方法直接调用, 无效
     * @Transactional 加于接口方法后, 被本类普通接口方法通过接口调用, 有效
     * @Transactional 加于接口方法后, 被它类的接口方法调用, 有效
     * @Transactional 加于接口方法后, 被它类的私有方法调用后, 有效
     */


    /**
     *
     * 多线程事务控制与上下文事务
     * 批量写操作异步处理（事务回滚）
     */
    @RequestMapping("/asyncBatchAdd")
    public Object asyncBatchAddProductTransational(){
        List<Product> list = new ArrayList<>();
        for (int i=0;i<5;i++){
            Product obj = new Product();
            obj.setPrice(100+i);
            obj.setName("name"+i);
            obj.setVersion(12);
            list.add(obj);
        }

        List<CompletableFuture<Pair<Boolean,String>>> futureList = new ArrayList<>();
        //批量保存异步处理
        list.forEach(entity ->{
            CompletableFuture<Pair<Boolean,String>> future  = CompletableFuture.supplyAsync(() ->{
                //异步线程中的事务
                log.info("异步线程事务");
                return productService.asyncAddProduct(entity);
            }).exceptionally((ex)->{
                log.error("try catch 异步线程异常");
                String msg = String.format("添加失败，名称：%s",entity.getName());
                return Pair.of(false,msg);
            });
            futureList.add(future);
        });
//        futureList.forEach(item ->{
//            item.join();
//        });
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()])).join();

        Map<Boolean,String> result = new HashMap<>();
        Set<String> errorList = new HashSet<>();
        futureList.forEach(future ->{
            Pair<Boolean,String> pair = future.getNow(null);
            if(pair.getLeft()){
                return;
            }
            String errorMsg = pair.getRight();
            errorList.add(errorMsg);
        });


        //上下文事务（与异步线程的事务式隔离开的，互不影响的）
        log.info("主线程上下文事务");
        try {
            Product obj2 = new Product();
            obj2.setPrice(10001);
            obj2.setName("aaaaa2");
            obj2.setVersion(28);
            productService.save(obj2);

            int m = 8/0;//异常
        } catch (Exception e) {
            log.error("上下文事务异常：{}",e);
            //e.printStackTrace();
        }

        return errorList;
    }





    /**
     *
     * 多线程事务控制与上下文事务
     * 批量写操作异步处理（事务回滚）
     */
    @RequestMapping("/asyncAdd")
    public String asyncBatchAddProduct(){
        List<Product> list = new ArrayList<>();
        for (int i=0;i<5;i++){
            Product obj = new Product();
            obj.setPrice(100+i);
            obj.setName("name"+i);
            obj.setVersion(12);
            list.add(obj);
        }

        List<CompletableFuture> futureList = new ArrayList<>();
        //批量保存异步处理
        list.forEach(item ->{
            CompletableFuture<Map<Boolean,String>> future = CompletableFuture.supplyAsync(() ->{
                //异步线程中的事务
                log.info("异步线程事务");
                return productService.addProduct(item);
            }).exceptionally((ex)->{
                log.error("异步线程运行异常");
                return null;
            });
            futureList.add(future);
        });
//        futureList.forEach(item ->{
//            item.join();
//        });
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()])).join();



        //上下文事务（与异步线程的事务式隔离开的，互不影响的）
        log.info("主线程上下文事务");
        Product obj2 = new Product();
        obj2.setPrice(10001);
        obj2.setName("aaaaa2");
        obj2.setVersion(28);
        productService.save(obj2);

        int m = 8/0;//异常

        return "sucess:异步";
    }



    /**
     *
     * 多线程事务控制与上下文事务
     * 批量写操作异步处理（事务回滚）
     */
    @RequestMapping("/asyncAddTry")
    public String asyncBatchAddProductTry(){
        List<Product> list = new ArrayList<>();
        for (int i=0;i<5;i++){
            Product obj = new Product();
            obj.setPrice(100+i);
            obj.setName("name"+i);
            obj.setVersion(12);
            list.add(obj);
        }

        List<CompletableFuture> futureList = new ArrayList<>();
        //批量保存异步处理
        list.forEach(item ->{
            CompletableFuture<Map<Boolean,String>> future = CompletableFuture.supplyAsync(() ->{
                //异步线程中的事务
                return productService.addProductTry(item);
            }).exceptionally((ex) ->{
                log.error("外层-异步线程异常");
                return null;
            });
            futureList.add(future);
        });
//        futureList.forEach(item ->{
//            item.join();
//        });
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()])).join();


        //TODO 获取异常，处理失败信息

        //上下文事务（与异步线程的事务式隔离开的，互不影响的）
        Product obj2 = new Product();
        obj2.setPrice(10001);
        obj2.setName("aaaaa2");
        obj2.setVersion(28);
        productService.save(obj2);

        System.out.println(8/0);//异常

        return "sucess:异步";
    }




    //////////////////////////////本类方法直接调用，事务无效, 通过接口调用，有效//////////////////////////////////
    /**
     * 本类方法直接调用，事务无效, 通过接口调用，有效
     * 本类方法直接调用事务注解方法无效
     * @param entity
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<Boolean,String> addProductTry(Product entity){

        Map<Boolean,String> result = new HashMap<>();
        log.info("异步处理");
        if("name2".equals(entity.getName())){
            try {
                System.out.println("添加异常："+entity.getName());
                productService.save(entity);
                result.put(false,"添加异常");
                System.out.println(10/0);
                return result;
            } catch (Exception e) {
                throw new RuntimeException("yic:error");
            }

        }else{
            System.out.println("添加成功："+entity.getName());
            productService.save(entity);
            result.put(true,"添加异常");
            return result;
        }

    }



}
