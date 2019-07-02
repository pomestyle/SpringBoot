package com.example.mongodbdemo.demo.m1dao;

import com.example.mongodbdemo.demo.test.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Administrator
 * @create: 2019-07-02 20:21
 **/
public interface MongoDao1 extends  MongoRepository<User, String> {
    //分页
    Page<User> findAll(Pageable var1);
}
