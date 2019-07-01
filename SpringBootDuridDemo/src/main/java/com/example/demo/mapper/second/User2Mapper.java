package com.example.demo.mapper.second;

import com.example.demo.pojo.User;

import java.util.List;


public interface User2Mapper {

    public List<User> findAll();

    public void batchInsert(List<User> userList);

    public void inserts(User user);
}
