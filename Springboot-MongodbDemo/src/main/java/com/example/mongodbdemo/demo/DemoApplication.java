package com.example.mongodbdemo.demo;

import com.example.mongodbdemo.demo.m1dao.MongoDao1;
import com.example.mongodbdemo.demo.m2dao.MongoDao2;
import com.example.mongodbdemo.demo.test.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@SpringBootApplication
@Controller
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Autowired
    private MongoDao1 primaryRepository;
    @Autowired
    private MongoDao2 secondaryRepository;

    @RequestMapping("test")
    public void TestSave() {
        //每页2条 按名字倒叙
        Pageable pageable = PageRequest.of(1,2, Sort.Direction.DESC,"userName");
        Page<User> all = primaryRepository.findAll(pageable);
        System.out.println(all.getContent().toString());
      /*  for (int i= 0;i<100;i++){
            this.primaryRepository.save(new User("小张1", "123456"));
        }*/
        //this.secondaryRepository.save(new User("小王1", "654321"));
      /*  List<User> primaries = this.primaryRepository.findAll();
        for (User primary : primaries) {
            System.out.println(primary.toString());
        }
        List<User> secondaries = this.secondaryRepository.findAll();
        for (User secondary : secondaries) {
            System.out.println(secondary.toString());
        }*/
    }
}
