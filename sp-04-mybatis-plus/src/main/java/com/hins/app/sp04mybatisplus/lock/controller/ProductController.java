package com.hins.app.sp04mybatisplus.lock.controller;


import com.hins.app.sp04mybatisplus.lock.entity.Product;
import com.hins.app.sp04mybatisplus.lock.mapper.ProductMapper;
import com.hins.app.sp04mybatisplus.lock.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.hins.app.sp04mybatisplus.common.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author nima
 * @since 2021-01-25
 */
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

}
