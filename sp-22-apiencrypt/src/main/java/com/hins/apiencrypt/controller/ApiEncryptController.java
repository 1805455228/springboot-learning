package com.hins.apiencrypt.controller;

import cn.licoy.encryptbody.annotation.encrypt.EncryptBody;
import cn.licoy.encryptbody.enums.EncryptBodyMethod;
import com.hins.apiencrypt.entity.AppCart;
import com.hins.apiencrypt.entity.AppUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : chenqixuan
 * @date : 2021/8/27
 */

@RestController
@RequestMapping("/test")
public class ApiEncryptController {

    @GetMapping(value = "/index")
    @EncryptBody(value = EncryptBodyMethod.AES)
    public String test(){

        return "hello world";
    }

    @GetMapping(value = "/getUser")
    @EncryptBody(value = EncryptBodyMethod.AES)
    public Object getUser(){
        AppUser user = new AppUser();

        AppCart appCart = new AppCart();
        appCart.setCartNum(135467L);
        appCart.setName("宝马X3");
        appCart.setAge(3);
        List<AppCart> cartList = new ArrayList<>();
        cartList.add(appCart);

        user.setUserId(121L);
        user.setUserName("cvv房");
        user.setAge(32);
        user.setCart(appCart);
        user.setCartList(cartList);

        return user;
    }
}

