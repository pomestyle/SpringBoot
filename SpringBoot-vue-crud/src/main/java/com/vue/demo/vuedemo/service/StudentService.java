package com.vue.demo.vuedemo.service;


import com.github.pagehelper.PageInfo;
import com.vue.demo.vuedemo.pojo.Student;



/**
 * @description:
 * @author: Administrator
 * @create: 2020-06-11 20:45
 **/
public interface StudentService {

    void add(Student student);

    PageInfo<Student> findList(Student student,int page, int pageSize);

    void update(Student student);


}
