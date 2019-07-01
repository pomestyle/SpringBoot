package com.example.demo.mapper.one;

import com.example.demo.pojo.User;

import java.util.List;


public interface User1Mapper {

    public List<User> findAll();

    public void batchInsert(List<User> userList);

    public void inserts(User user);
}
