package com.hins.sp09redis.controller;

import com.hins.sp09redis.services.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @author : chenqixuan
 * @date : 2021/2/3
 */
@RestController
@RequestMapping("/sign")
public class SignController {


    @Autowired
    private SignService signService;


    @PostMapping
    public void sign(@RequestParam Long userId) {
        signService.checkIn(userId, LocalDate.now());
    }

    @GetMapping
    public Object getContinuousSignCount(@RequestParam Long userId) {
        return signService.getContinuousSignCount(userId, LocalDate.now());
    }

    @GetMapping("/firstDate")
    public Object getFirstSignDate(@RequestParam Long userId) {
        return signService.getFirstSignDate(userId, LocalDate.now());
    }

    @GetMapping("/count")
    public Object getSignCount(@RequestParam Long userId) {
        return signService.getSignCount(userId, LocalDate.now());
    }

    @GetMapping("/info")
    public Object info(@RequestParam Long userId) {
        return signService.getSignInfo(userId, LocalDate.now());
    }


}
