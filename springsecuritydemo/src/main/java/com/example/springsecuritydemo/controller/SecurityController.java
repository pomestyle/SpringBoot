package com.example.springsecuritydemo.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SecurityController {
 /*   @RequestMapping("/")
    public String index() {
        return "index";
    }
*/

    @RequestMapping("/content")
    public String content() {
        return "content";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    //方法执行前检查权限 @PreAuthorize 可以将登录用户的角色 / 权限参数传到方法中。
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/admin/admin2")
    @ResponseBody
    public String admin2() {

        return "admin2";
    }


}
