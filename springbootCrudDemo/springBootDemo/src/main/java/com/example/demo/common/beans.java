package com.example.demo.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.pojo.User;

@Configuration	//相当于  xml  beans  标签
public class beans {

	
	@Bean("user1")    //  bean
	public User getUser1() {
		return new User();
	}
	
	@Bean("user2")
	public User getUser2222() {
		
		User u = new User();
		u.setAge(111);
		return u;
	}
}
