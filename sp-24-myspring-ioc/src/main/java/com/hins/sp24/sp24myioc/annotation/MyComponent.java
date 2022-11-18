package com.hins.sp24.sp24myioc.annotation;


import com.hins.sp24.sp24myioc.constant.ScopeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 组件注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyComponent {
    //component name
    public String name() default "";
    public String scope() default ScopeType.SINGLETON;
}
