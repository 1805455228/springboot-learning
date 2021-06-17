package com.hins.sp20websocket.controller;


import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

/**
 * @author qixuan.chen
 * @date 2020-03-11 18:54
 */
public class WebResult  extends HashMap<String,Object> {

    private int code;
    private String msg;

    public WebResult(){
        put("code", 0);
        put("msg", "success");
    }

    public static WebResult success() {
        WebResult webResult = new WebResult();
        webResult.put("code", 0);
        webResult.put("msg", "请求成功");
        return webResult;
    }

    public static WebResult success(String msg) {
        WebResult webResult = new WebResult();
        webResult.put("code", 0);
        webResult.put("msg", msg);
        return webResult;
    }

    public static WebResult success(Object data) {
        WebResult webResult = new WebResult();
        webResult.put("code", 0);
        webResult.put("data", data);
        return webResult;
    }

    public static WebResult error() {
        WebResult webResult = new WebResult();
        webResult.put("code", 1);
        webResult.put("msg", "请求失败");
        return webResult;
    }

    public static WebResult error(String msg) {
        WebResult webResult = new WebResult();
        webResult.put("code", 1);
        webResult.put("msg", msg);
        return webResult;
    }

    public static WebResult error(int code, String msg) {
        WebResult webResult = new WebResult();
        webResult.put("code", code);
        webResult.put("msg", msg);
        return webResult;
    }
    public WebResult(int code){
        this.code = code;
    }

    public WebResult(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public WebResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
