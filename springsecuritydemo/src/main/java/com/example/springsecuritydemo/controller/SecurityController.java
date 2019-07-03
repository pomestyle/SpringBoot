package com.example.springsecuritydemo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SecurityController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping("/content")
    public String content() {
        return "content";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }



    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }
}
