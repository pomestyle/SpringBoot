# Spring Boot 整合Quartz以及动态控制任务(暂停，启动，修改执行时间)

### 整合 Quartz定时器

```xml
     <!-- quartz 坐标 -->
        <dependency>
            <groupId>org.quartz-scheduler</groupId>
            <artifactId>quartz</artifactId>
            <version>2.2.1</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework/spring-tx -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>5.0.8.RELEASE</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework/spring-context-support -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>4.3.13.RELEASE</version>
        </dependency>

```


### 集成接口式定时器

1 集成Job  
```java
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

```


### Quartz 启动 更新 停止


#### 新建任务

```java
@Component
public class Job {


    public void job() {

        System.out.println("--------------- 执行任务 --------------");
    }
}

```

#### 配置定时器

```java
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
```

#### 对外提供配置启动更新接口

```java
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
```


