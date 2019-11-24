package com.hins.sp07springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author qixuan.chen
 * @date 2019-07-07 14:03
 */
@Controller
public class HomeController {

    @RequestMapping("index")
    public String home(){

        return "index";
    }

    @RequestMapping("vip1")
    public ModelAndView vip1(){
        ModelAndView mav = new ModelAndView();

        mav.addObject("name","hins chan");
        mav.setViewName("book/list");
        return mav;
    }

    @RequestMapping("vip2")
    public ModelAndView vip2(){
        ModelAndView mav = new ModelAndView();

        mav.addObject("name","hins chan");
        mav.setViewName("book/list");
        return mav;
    }

    @RequestMapping("vip3")
    public ModelAndView vip3(){
        ModelAndView mav = new ModelAndView();

        mav.addObject("name","hins chan");
        mav.setViewName("book/list");
        return mav;
    }
}
