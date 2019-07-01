package com.example.demo.mapper;

import java.util.List;

import com.example.demo.pojo.User;


public interface userMapper {

	public List<User> findAll();
	public void batchInsert(List<User> userList);
}
