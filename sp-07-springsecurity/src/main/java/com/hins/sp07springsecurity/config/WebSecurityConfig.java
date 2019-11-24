package com.hins.sp07springsecurity.config;

import com.hins.sp07springsecurity.security.MyAuthenctiationFailureHandler;
import com.hins.sp07springsecurity.security.MyAuthenctiationSuccessHandler;
import com.hins.sp07springsecurity.service.impl.MyUserDetailsService;
import com.hins.sp07springsecurity.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author qixuan.chen
 * @date 2019-07-07 22:33
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //  启用方法级别的权限认证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

    @Autowired
    private MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /**
     * http请求授权规则
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()                 // 定义哪些URL需要被保护、哪些不需要被保护
            .antMatchers("/", "/index","/login","/sys/login").permitAll()       // 设置所有人都可以访问登录页面
            .anyRequest().authenticated()               // 任何请求,登录后可以访问

            .and()
            .formLogin()                    // 定义当需要用户登录时候，转到的登录页面。
            .loginPage("/login")                        // 设置登录页面
            .loginProcessingUrl("/login")                   // 自定义的登录接口
            //.defaultSuccessUrl("/book/list").permitAll()      // 登录成功之后，默认跳转的页面 从哪里转过来，登录通过后就跳转到那个URL
            //.successForwardUrl("/index").permitAll()        // 登录成功之后，默认跳转的页面 登录后固定跳转URL
            //.failureUrl("/login-error").permitAll()         //
            .successHandler(myAuthenctiationSuccessHandler)   //登录成功返回Json
            .failureHandler(myAuthenctiationFailureHandler)   //登录失败返回Json


            .and()
            .logout()
            //.logoutSuccessHandler(LogoutSuccessHandler)     //退出注销
            .logoutSuccessUrl("/index");


            //开启跨域访问
            http.cors().disable();

            http.csrf().disable();                    // 关闭csrf防护
    }

    /**
     * 认证管理
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }


    /**
     * 允许访问的URL
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**/*", "/**/*.css", "/**/*.js");
    }

    /**
     * 密码加密器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 密码加密器 (md5)
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoderByMd5() {
        PasswordEncoder passwordEncoder = new PasswordEncoder(){

            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String)rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String)rawPassword));
        }};

        return passwordEncoder;
    }


    /**
     * 测试密码加密器
     * @param args
     */
    public static void main(String args[]){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String encodedPassword = passwordEncoder.encode("123456");
        System.out.println(encodedPassword);

    }

}
