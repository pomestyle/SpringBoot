package com.vue.demo.vuedemo.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vue.demo.vuedemo.dao.StudentDao;
import com.vue.demo.vuedemo.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: Administrator
 * @create: 2020-06-11 20:45
 **/
@Service
public class StudentServiceImpl implements StudentService {


    @Autowired
    private StudentDao studentDao;


    @Override
    public PageInfo<Student> findList(Student student, int page, int pageSize) {

        PageHelper.startPage(page, pageSize);
        List<Student> all = studentDao.findAll(student);
        return new PageInfo<>(all);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Student student) {
        studentDao.update(student);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Student student) {
        studentDao.insert(student);
    }

}
