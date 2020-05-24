package com.example.demo.cifg;


import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 配置类
 */
@Configuration
public class QuartzConfig {

    // 定义方法，做什么
    @Bean(name = "job1")
    public MethodInvokingJobDetailFactoryBean job1(Job job) {
        MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        factoryBean.setConcurrent(false);
        // 使用哪个对象
        factoryBean.setTargetObject(job);
        // 使用哪个方法
        factoryBean.setTargetMethod("job");

        return factoryBean;
    }

    // 定义什么时候做，使用 cron 表达式
    @Bean(name = "cron1")
    public CronTriggerFactoryBean cron1(@Qualifier("job1") MethodInvokingJobDetailFactoryBean job1) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        // 设置job对象
        factoryBean.setJobDetail(job1.getObject());
        // 设置执行时间
        factoryBean.setCronExpression("0/5 * * * * ?");
        return factoryBean;
    }


    //  创建 Scheduler 对象
    //  定义 任务,传入 triggers
    @Bean(name = "sch1")
    public SchedulerFactoryBean scheduler1(Trigger... triggers) {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        // 设置 triggers
        factoryBean.setTriggers(triggers);
        // 自动运行
        factoryBean.setAutoStartup(true);

        return factoryBean;
    }

}