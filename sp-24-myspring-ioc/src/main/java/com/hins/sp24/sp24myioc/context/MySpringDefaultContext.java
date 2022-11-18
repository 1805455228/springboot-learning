package com.hins.sp24.sp24myioc.context;

import com.hins.sp24.sp24myioc.annotation.MyAutowired;
import com.hins.sp24.sp24myioc.annotation.MyComponent;
import com.hins.sp24.sp24myioc.annotation.MyScope;
import com.hins.sp24.sp24myioc.constant.ScopeType;
import com.hins.sp24.sp24myioc.exception.MyAutowiredException;
import com.hins.sp24.sp24myioc.exception.MyBeanNotFoundException;
import com.hins.sp24.sp24myioc.exception.NotSupportException;
import com.hins.sp24.sp24myioc.interfaces.MySpringContext;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 默认上下文
 */
public class MySpringDefaultContext implements MySpringContext {
    public Object getBean(String name) {
        Object bean=IOC.get(name);
        if(bean!=null){
            return bean;
        }
        return null;
    }

    /**
     * 概览
     */
    public void overview(){
        System.out.println("\n\n");
        System.out.println("-----------------IOC容器概览---------------------");
        System.out.println("IOC容器中共有"+IOC.size()+"个bean");
        for(String name:IOC.keySet()){
            System.out.println(name);
        }
        System.out.println("-------------------------------------------------");
        System.out.println("\n\n");
    }

    /**
     * 获取bean
     * @param cls bean的类型
     * @return
     */
    public Object getBean(Class cls) {
        MyComponent myComponent=(MyComponent)cls.getAnnotation(MyComponent.class);
        MyScope myScope=(MyScope) cls.getAnnotation(MyScope.class);
        String type= ScopeType.SINGLETON;
        if((myComponent!=null&&myComponent.scope().equals(ScopeType.PROTOTYPE))||(myScope!=null&&myScope.value().equals(ScopeType.PROTOTYPE)))
        {
            type=ScopeType.PROTOTYPE;
        }
        if(type.equals(ScopeType.SINGLETON)){
            //单例模式
            String name=genName(cls);
            Object object=getBean(name);
            //直接找这个类的实例
            if(object!=null){
                return object;
            }
            if(cls.isAnnotation()){
                throw new NotSupportException("类型：注解"+cls.getName());
            }
            //找同类型的且优先同类型
            List<Object> instances=new ArrayList<Object>();
            for(Map.Entry<String,Object> entry:IOC.entrySet()){
                if(cls.isInstance(entry.getValue())){
                    instances.add(entry.getValue());
                }
            }
            //找不到这个类的实例如果是个接口就找这个类实现类的实例
            if(cls.isInterface()){
                //找实现类的实例
                for (Object o:instances){
                    if(cls.isInstance(o)){
                        return o;
                    }
                }
                //找不到实现类的实例对象
                throw  new MyBeanNotFoundException("找不到"+cls.getName()+"的任何实现类bean");
            }

            //先找同类型
            for (Object o:instances){
                if (cls.equals(o.getClass())){
                    return o;
                }
            }
            //后找子类
            for (Object o:instances){
                if (cls.isInstance(o)){
                    return o;
                }
            }
            //子类的实例也找不到就去创建一个
            return genObjectByClass(cls);
        }else{
            //多例模式

            //找同类型的且优先同类型
            List<Object> instances=new ArrayList<Object>();
            for(Map.Entry<String,Object> entry:IOC.entrySet()){
                if(cls.isInstance(entry.getValue())){
                    instances.add(entry.getValue());
                }
            }
            //找不到这个类的实例如果是个接口就找这个类实现类的实例
            if(cls.isInterface()){
                //找实现类的实例
                for (Object o:instances){
                    if(cls.isInstance(o)){
                        return genObjectByClass(o.getClass());
                    }
                }
                //找不到实现类的实例对象
                throw  new MyBeanNotFoundException("找不到"+cls.getName()+"的任何实现类bean");
            }
            //先找同类型
            for (Object o:instances){
                if (cls.equals(o.getClass())){
                    return genObjectByClass(o.getClass());
                }
            }
            //后找子类
            for (Object o:instances){
                if (cls.isInstance(o)){
                    return genObjectByClass(o.getClass());
                }
            }
            return genObjectByClass(cls);
        }
    }

    /**
     * 根据类生成对象实例
     * @param cls
     * @return
     */
    protected Object genObjectByClass(Class cls){
        try {
            Object object=cls.newInstance();
            for(Field field:cls.getDeclaredFields()){
                MyAutowired annotation=field.getAnnotation(MyAutowired.class);
                //是否存在需要注入的方法
                if(annotation!=null){
                    field.setAccessible(true);
                    //如果注入的时候传入了bean的名字
                    String name=annotation.value();
                    Object fieldObj;
                    if(name.equals("")){
                        fieldObj=getBean(field.getType());
                    }else{
                        fieldObj=getBean(name);
                    }
                    if(fieldObj!=null){
                        field.set(object,fieldObj);
                    }else{
                        throw new MyAutowiredException("创建对象时自动注入异常"+cls.getName()+":"+field.getName());
                    }
                }
            }
            String name=genName(cls);
            IOC.put(name,object);
            return object;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 销毁
     */
    public void dispose() {
        IOC.clear();
        ATTRS.clear();
    }

    public void setBean(String name, Object object) {
        IOC.put(name,object);
        Class cls=object.getClass();
    }
    public void setBean(Class cls, Object object) {
        String name=genName(cls);
        IOC.put(name,object);
    }
    private String genName(Class cls){
        return cls.getName();
    }

    public Object getAttr(Object object) {
        return ATTRS.get(object);
    }

    public void setAttrs(Object key, Object value) {
        ATTRS.put(key,value);
    }
}
