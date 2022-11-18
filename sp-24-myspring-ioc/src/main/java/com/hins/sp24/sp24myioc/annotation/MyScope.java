package com.hins.sp24.sp24myioc.annotation;

import com.hins.sp24.sp24myioc.constant.ScopeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * scope注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyScope {
    public String value() default ScopeType.SINGLETON;
}
