package com.example.springbootatomikos;

import com.example.springbootatomikos.pojo.User;
import com.example.springbootatomikos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootatomikosApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootatomikosApplication.class, args);
    }

    @Autowired
    private UserService userService;

    @RequestMapping("test")
    public void test(){
        User user = new User();
        user.setName("lynn");
        user.setAge(10);
        try {
            userService.addUser(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
