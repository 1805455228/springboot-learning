package com.hins.sp02servlet.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author qixuan.chen
 * @date 2019-07-04 15:38
 * 注册器名称为customFilter,拦截的url为所有
 */


@WebFilter(filterName="customFilter",urlPatterns={"/*"})
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter 初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        System.out.println("doFilter 请求处理");
        //对request、response进行一些预处理
        // 比如设置请求编码
        // request.setCharacterEncoding("UTF-8");
        // response.setCharacterEncoding("UTF-8");
        //TODO 进行业务逻辑

        //链路 直接传给下一个过滤器
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("filter 销毁");
    }
}
