package com.example.demo;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.web.bind.annotation.RequestMapping;


@SpringBootApplication
@EnableCaching      //开启redis缓存
@MapperScan("com.example.demo.mapper")   //扫描全部mapper

public class SpringBootDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}
}
