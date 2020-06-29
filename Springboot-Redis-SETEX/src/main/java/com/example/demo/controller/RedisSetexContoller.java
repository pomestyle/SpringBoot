package com.example.demo.controller;

import com.example.demo.redis.config.DistributedLockConfig;
import com.example.demo.redis.pojo.Lock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description:  基于redis setex 分布式锁
 * @author: Administrator
 * @create: 2020-05-01 22:28
 **/
@RestController
public class RedisSetexContoller {

    @Autowired
    DistributedLockConfig distributedLockConfig;


    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping("test")
    public String index0() {


        Lock lock = new Lock("test", "test");
        if (distributedLockConfig.tryLock(lock)) {
            try {
                //为了演示锁的效果，这里睡眠5000毫秒
                System.out.println("执行方法");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            distributedLockConfig.releaseLock(lock);
        }
        return "hello world!";
    }



    @RequestMapping("test1")
    public void test() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(50);
        for (int i = 0; i < 40; i++) {
            scheduledExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    Lock lock = new Lock("a", "a1234" + 1);
                    try {
                        //阻塞5s
                        Thread.sleep(5_000);
                        if (distributedLockConfig.tryLock(lock, 20, 10, 10)) {
                            System.out.println(Thread.currentThread().getName() + "  ---- " + LocalDateTime.now() + " --- 执行成功");
                        } else {
                            System.out.println(Thread.currentThread().getName() + "  ---- " + LocalDateTime.now() + " --- 执行成功");
                        }
                        //释放锁
                        distributedLockConfig.releaseLock(lock);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

}
