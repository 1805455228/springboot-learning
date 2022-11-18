package com.hins.sp24.sp24myioc.exception;

/**
 * 我的自动注入时异常
 */
public class MyAutowiredException extends RuntimeException {
    private static final String msg="自动填充异常：";
    public MyAutowiredException(String message){
        super(msg+message);
    }
}
