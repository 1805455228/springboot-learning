package com.hins.sp02servlet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qixuan.chen
 * @date 2019-07-04 15:46
 */

@RestController
public class TestFirterController {

    @GetMapping(value="/filter")
    public String getFilter(){

        return "filter test";

    }
}
