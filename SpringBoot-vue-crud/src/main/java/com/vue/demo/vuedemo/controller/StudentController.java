package com.vue.demo.vuedemo.controller;

import com.github.pagehelper.PageInfo;
import com.vue.demo.vuedemo.common.PageQuery;
import com.vue.demo.vuedemo.common.ReturnMessage;
import com.vue.demo.vuedemo.pojo.Student;
import com.vue.demo.vuedemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stu")
@CrossOrigin
public class StudentController {


    @Autowired
    private StudentService studentService;

    @RequestMapping("findAlls")
    public ReturnMessage findAlls(@RequestBody PageQuery<Student> pageQuery) {
        return new ReturnMessage(0, "成功", studentService.findList(pageQuery.getObject(), pageQuery.getPage(), pageQuery.getPageSize()));
    }


    @RequestMapping("add")
    public ReturnMessage add(@RequestBody Student student) {
        studentService.add(student);
        return new ReturnMessage(0, "成功", null);
    }


    @RequestMapping("update")
    public ReturnMessage findAll(@RequestBody Student student) {
        studentService.update(student);
        return new ReturnMessage(0, "成功");
    }

}
