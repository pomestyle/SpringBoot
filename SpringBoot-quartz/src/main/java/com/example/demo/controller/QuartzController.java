package com.example.demo.controller;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 定时器控制层
 * @author: Administrator
 * @create: 2020-05-24 15:35
 **/
@RestController
public class QuartzController {


    @Autowired
    @Qualifier("sch1")
    private Scheduler scheduler;

    /**
     * 关闭任务
     */
    @RequestMapping("/pause")
    public String pause() throws Exception {

        System.out.println(scheduler);
        //任务名
        JobKey key = new JobKey("job1");
        scheduler.pauseJob(key);
        System.out.println(" ----------  关闭成功 ---------- ");
        return "pause";


    }

    /**
     * 执行任务
     */
    @RequestMapping("/start")
    public String start() throws Exception {

        System.out.println(scheduler);

        JobKey key = new JobKey("job1");
        scheduler.resumeJob(key);
        System.out.println(" ----------  启动成功 ---------- ");
        return "start";
    }


    /**
     * 动态修改任务执行的时间
     */
    @RequestMapping("/trigger")
    public String trigger() throws Exception {
        // 获取任务
        JobKey jobKey = new JobKey("job1");
        // 获取 jobDetail
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        // 生成 trigger
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                .build();
        // 删除任务，不删除会报错。报任务已存在
        scheduler.deleteJob(jobKey);
        // 启动任务
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println(" ----------  更新启动成功 ---------- ");
        return "trigger";
    }
}