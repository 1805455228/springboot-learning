package com.hins.sp21websocket.api.code;

/**
 * 枚举了一些常用API操作码.
 * @author zhangyong
 */
public enum ResultCode implements IErrorCode {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 无改动
     */
    NOT_MODIFIED(304, "未修改"),
    /**
     * 操作失败
     */
    SERVER_ERROR(500, "操作失败"),
    /**
     * 服务器上无法找到请求的资源
     */
    NOT_FOUND(404, "服务器上无法找到请求的资源"),
    /**
     * 暂未登录或token已经过期
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    /**
     * 没有相关权限
     */
    FORBIDDEN(403, "没有相关权限"),
    /**
     * 全局开关
     */
    GLOBAL_SWITCH(2002, "全局开关关闭"),
    ;

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
