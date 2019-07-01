package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.pojo.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemoApplicationTests {

	@Test
	public void contextLoads() {
		
		
	}

	
	@Test
	public void tesst() {
		ApplicationContext annotationContext = new AnnotationConfigApplicationContext("common");
		User u1 = (User) annotationContext.getBean("user1");
		User u2 = (User) annotationContext.getBean("user2");

		User c = annotationContext.getBean("user1", User.class);// 创建 c = annotationContext.getBean("counter", Counter.class);// 创建
		 
		System.out.println(u1+"\n" + u2  + c);
	}
}
