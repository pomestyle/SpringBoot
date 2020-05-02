package com.example.springbootatomikos.service;

import com.example.springbootatomikos.mapper.one.UserMapper1;
import com.example.springbootatomikos.mapper.two.UserMapper2;
import com.example.springbootatomikos.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @description: 业务类
 * @author: Administrator
 * @create: 2020-05-02 19:27
 **/
@Service
public class UserService {

    @Autowired
    private UserMapper1 userMapper1;
    @Autowired
    private UserMapper2 userMapper2;

    @Transactional
    public void addUser(User user)throws Exception{
        userMapper1.addUser(user.getName(),user.getAge());
        int a= 1/0;
        userMapper2.addUser(user.getName(),user.getAge());
    }
}