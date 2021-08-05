package com.hins.sp21websocket.api.code;

/**
 * 封装API的错误码
 * @author zhangyong
 */
public interface IErrorCode {
    /**
     * 获取错误码
     * @return 错误码
     */
    int getCode();

    /**
     * 获取错误描述
     * @return 错误描述
     */
    String getMessage();
}
