package com.hins.sp24.sp24myioc.exception;

/**
 * bean无法找到异常
 */
public class MyBeanNotFoundException extends RuntimeException {
    private static final String msg="bean找不到：";
    public MyBeanNotFoundException(String message){
        super(msg+message);
    }
}
