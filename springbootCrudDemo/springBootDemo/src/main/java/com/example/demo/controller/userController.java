package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.pojo.User;
import com.example.demo.serviceImp.userServiceImp;

//@Controller

@RestController
public class userController {

	@Autowired
	private userServiceImp userService;
	
	@Autowired
	@Qualifier("user1")
	private User user1;
	
	@Autowired
	@Qualifier("user2")
	private User user2;

	@RequestMapping("userLists")
	public List<User> showUsers() {
		
		System.err.println(user1);
		System.err.println(user2);
		List<User> list = userService.findAll();

		return list;
	}

	@RequestMapping("showUser")
	public String showUser(Model model) {
		List<User> list = userService.findAll();

		model.addAttribute("user", list.get(0));
		
		
		return "msg";
	}

	@RequestMapping("showIndex")
	public ModelAndView showindex(Model model) {

		ModelAndView mv = new ModelAndView("userList");
		List<User> list = userService.findAll();

		model.addAttribute("user", list.get(0));
		return mv;
	}
	
	@RequestMapping("msg")
	public ModelAndView showindexs() {

		ModelAndView mv = new ModelAndView("index");
		return mv;
	}

}
