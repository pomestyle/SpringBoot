package com.example.springboothandlerinterceptor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: Administrator
 * @create: 2019-07-06 17:29
 **/
@RestController
public class TestController {

    @GetMapping("/")
    public String getRequestInfo(HttpServletRequest request) {
        System.out.println("getRequestInfo "  );
        return "index";
    }

}