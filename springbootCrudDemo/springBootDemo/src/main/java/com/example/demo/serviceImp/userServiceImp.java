package com.example.demo.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.userMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.userService;

@Service
public class userServiceImp implements userService {

	@Autowired
	private userMapper userMapper;
	
	//设置缓存
	@Cacheable(value="findAll")
	public List<User> findAll() {
		// TODO Auto-generated method stub
		System.err.println("第二次没打印则说明，缓存成功。。。");
		List<User> list = userMapper.findAll();
		return list;
		
	}

	@Override
	@Transactional
	public void batchInsert(List<User> userList) {
	
		userMapper.batchInsert(userList);
		
	}

}
