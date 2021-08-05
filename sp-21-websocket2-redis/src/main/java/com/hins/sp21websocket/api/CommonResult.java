package com.hins.sp21websocket.api;


import com.hins.sp21websocket.api.code.IErrorCode;
import com.hins.sp21websocket.api.code.ResultCode;
import lombok.Data;

/**
 * 通用返回对象
 * @author Administrator
 */
@Data
public class CommonResult<T> {
    private int code;
    private String message;
    private T data;

    public CommonResult() {
    }

    protected CommonResult(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    protected CommonResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 
     * @param <T>
     * @param data
     * @return
     */
    public static <T> CommonResult<T> result(T data) {
    	if(data != null) {
    		return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    	}
    	return new CommonResult<T>(ResultCode.SERVER_ERROR.getCode(), ResultCode.SERVER_ERROR.getMessage());
    } 
    
    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.SERVER_ERROR.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param errorCode 提示错误码
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(int errorCode, String message) {
        return new CommonResult<T>(errorCode, message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.SERVER_ERROR);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> notModified(T data) {
        return new CommonResult<T>(ResultCode.NOT_MODIFIED.getCode(), ResultCode.NOT_MODIFIED.getMessage(), data);
    }
}
