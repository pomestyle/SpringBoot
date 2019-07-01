package com.example.demo.controller;


import com.example.demo.mapper.one.User1Mapper;
import com.example.demo.mapper.second.User2Mapper;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    User1Mapper user1Mapper;

    @Autowired
    User2Mapper user2Mapper;


    @RequestMapping("add")
    public void add() {

        user1Mapper.inserts(new User("a123456", 1));
        user1Mapper.inserts(new User("b123456", 1));
        user2Mapper.inserts(new User("b123456", 1));
    }


}
