package com.hins.sp24.sp24myioc.exception;

/**
 * 注入失败
 */
public class NotSupportException extends RuntimeException {
    private static final String msg="不支持注入：";
    public NotSupportException(String message){
        super(msg+message);
    }
}
