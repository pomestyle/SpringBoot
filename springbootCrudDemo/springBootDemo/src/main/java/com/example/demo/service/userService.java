package com.example.demo.service;

import java.util.List;

import com.example.demo.pojo.User;

public interface userService {

	public List<User> findAll();
	//批量插入
	public void batchInsert(List<User> userList);
}
