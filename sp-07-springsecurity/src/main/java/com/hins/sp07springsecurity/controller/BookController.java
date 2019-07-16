package com.hins.sp07springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author qixuan.chen
 * @date 2019-07-08 21:34
 */
@Controller
@RequestMapping("/book")
public class BookController {

    @PreAuthorize("hasAuthority('BookList')")
    @GetMapping("/list")
    public String list() {
        return "book/list";
    }

    @PreAuthorize("hasAuthority('BookAdd')")
    @GetMapping("/add")
    public String add() {
        return "book/add";
    }

    @PreAuthorize("hasAuthority('BookDetail')")
    @GetMapping("/detail")
    public String detail() {
        return "book/detail";
    }
}