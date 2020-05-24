package com.example.demo.cifg;

import org.quartz.*;
import org.quartz.Job;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @description: 控制台定时器
 * @author: Administrator
 * @create: 2020-05-24 16:09
 **/
public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {


        System.out.println(" ------------ quartz 控制台定时器 ------------------ ");
    }


    public static void main(String[] args) throws SchedulerException {


        // 1,创建 job，你要做什么事？
        JobDetail job = JobBuilder.newJob(TestJob.class).build();

        // 2,创建  trigger ，你在什么时候做？
        Trigger trigger = TriggerBuilder
                .newTrigger()
                //.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2)).build();
                //可以换成cron表达式
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?")).build();

        // 3,创建 schedule 对象，在什么时候执行什么事？
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.scheduleJob(job, trigger);
        // 4,启动
        scheduler.start();

    }
}
