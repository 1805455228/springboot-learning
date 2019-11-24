package com.hins.sp01hello.springbean;

import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author qixuan.chen
 * @date 2019-09-15 11:16
 */
@RestController
public class SpringBean {

    @Autowired
    private SpringBean2 springBean2;

    private String id;

    private Integer age;

    public String getId() {
        System.out.println("=======get id属性======");
        return id;
    }

    public void setId(String id) {
        System.out.println("=======set id属性======");
        this.id = id;
    }

    public Integer getAge() {
        System.out.println("=======get age属性======");
        return age;
    }

    public void setAge(Integer age) {
        System.out.println("=======set age属性======");
        this.age = age;
    }

    public SpringBean() {
        System.out.println("=====11new 对象（实例化）======"+Thread.currentThread().getName());
    }

    @PostConstruct
    public void init(){
        System.out.println("1111@Bean-init-method//1在构造函数执行完之后执行");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("111@Bean-destory-method//2在bean销毁之前执行");
    }



    @RequestMapping("/bean/test")
    public String test(){
        try {
            springBean2.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "success";
    }





}
