package com.example.mongodbdemo.demo.m2dao;

import com.example.mongodbdemo.demo.test.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Administrator
 * @create: 2019-07-02 20:21
 **/
public interface MongoDao2 extends  MongoRepository<User, String> {
}
