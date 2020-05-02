package com.example.springbootatomikos.mapper.two;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @description:
 * @author: Administrator
 * @create: 2020-05-02 19:27
 **/
@Mapper
public interface UserMapper2 {

    @Insert("insert into test_user2(name,age) values(#{name},#{age})")
    void addUser(@Param("name") String name, @Param("age") int age);
}
