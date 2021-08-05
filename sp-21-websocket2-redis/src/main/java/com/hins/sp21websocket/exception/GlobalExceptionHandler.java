package com.hins.sp21websocket.exception;


import com.alibaba.druid.support.json.JSONUtils;
import com.hins.sp21websocket.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常处理
 *
 * @author zhangyong
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public void handle(ApiException e, HttpServletRequest request, HttpServletResponse response) {
        CommonResult result;
        if (e.getErrorCode() != null) {
            result = CommonResult.failed(e.getErrorCode());
        } else {
            result = CommonResult.failed(e.getMessage());
        }
        String json = JSONUtils.toJSONString(result);
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        try {
            response.getWriter().write(json);
        } catch (IOException ex) {
            log.error("接口[{}]IO异常, ", request.getRequestURI(), e);
        }
    }

    @ExceptionHandler(value = Exception.class)
    public void handle(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error("接口[{}]异常, ", request.getRequestURI(), e);
        CommonResult result = CommonResult.failed("系统出了点小问题");
        String json = JSONUtils.toJSONString(result);
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        try {
            response.getWriter().write(json);
        } catch (IOException ex) {
            log.error("接口[{}]IO异常, ", request.getRequestURI(), e);
        }
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public void handle(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) {
        CommonResult result;
        result = CommonResult.failed(e.getBindingResult().getFieldError().getDefaultMessage());
        String json = JSONUtils.toJSONString(result);
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        try {
            response.getWriter().write(json);
        } catch (IOException ex) {
            log.error("接口[{}]IO异常, ", request.getRequestURI(), e);
        }
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public void handle(HttpRequestMethodNotSupportedException e, HttpServletRequest request, HttpServletResponse response) {
        CommonResult result;
        result = CommonResult.failed("http请求方式不支持");
        String json = JSONUtils.toJSONString(result);
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        try {
            response.getWriter().write(json);
        } catch (IOException ex) {
            log.error("接口[{}]IO异常, ", request.getRequestURI(), e);
        }
    }

    /**
     * servlet绑定参数缺失异常处理
     *
     * @param e
     * @param request
     * @param response
     */
    @ExceptionHandler(value = ServletRequestBindingException.class)
    public void handle(ServletRequestBindingException e, HttpServletRequest request, HttpServletResponse response) {
        CommonResult result;
        result = CommonResult.failed(e.getMessage());
        String json = JSONUtils.toJSONString(result);
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        try {
            response.getWriter().write(json);
        } catch (IOException ex) {
            log.error("接口[{}]IO异常, ", request.getRequestURI(), e);
        }
    }

}
