package com.vue.demo.vuedemo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @description:
 * @author: Administrator
 * @create: 2020-06-20 18:04
 **/
@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoginController {


    @RequestMapping("/login")
    public Map<String,String> login(@RequestBody Map<String,String> map) throws Exception {

        System.out.println(map);

        return map;


    }
}
