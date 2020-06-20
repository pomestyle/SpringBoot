package com.vue.demo.vuedemo.dao;


import com.vue.demo.vuedemo.pojo.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @create: 2020-06-11 20:38
 **/
@Mapper
public interface StudentDao {


    List<Student> findAll(Student student);


    void insert(Student user);


    void update(Student student);

}
