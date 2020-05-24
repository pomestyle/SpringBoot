package com.example.demo.cifg;

import org.springframework.stereotype.Component;

/**
 * @description: 定义任务job
 * @author: Administrator
 * @create: 2020-05-24 15:39
 **/
@Component
public class Job {


    public void job() {

        System.out.println("--------------- 执行任务 --------------");
    }
}
