package com.hins.sp24.sp24myioc.interfaces;

import java.util.HashMap;
import java.util.Map;

/**
 * spring 上下文接口
 */
public interface MySpringContext {
     //IOC容器
     Map<String, Object> IOC=new HashMap<String, Object>();
     //存储上下文的属性
     Map<Object,Object> ATTRS=new HashMap<Object, Object>();

     /**
      * 根据名称获取 bean 实例
      * @param name bean实例的名称
      * @return Object bean实例
      */
     Object getBean(String name);

     /**
      * 获取 bean
      * @param cls bean的类型
      * @return Object bean实例
      */
     Object getBean(Class cls);

     /**
      * 获取上下文的属性
      * @param object 键
      * @return Object 值
      */
     Object getAttr(Object object);

     /**
      * 设置上下文属性
      * @param key 键
      * @param value 值
      */
     void setAttrs(Object key,Object value);

     /**
      * 销毁所有实例
      */
     void dispose();

     /**
      * 设置 bean
      * @param name 名称
      * @param object bean实例
      */
     void setBean(String name,Object object);

     /**
      * 打印下扫描加载了多少个类
      */
     void overview();
}
