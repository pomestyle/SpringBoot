package com.example.demo.controller;


import com.example.demo.redis.redlock.AquiredLockWorker;
import com.example.demo.redis.redlock.RedisLocker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @description: Redlock 分布式锁
 * @author: Administrator
 * @create: 2020-05-01 22:28
 **/
@RestController
public class RedisRedLockContoller {


    @Autowired
    RedisLocker redisLocker;


    @RequestMapping("redLock")
    public String index() throws Exception {

        redisLocker.lock("test", new AquiredLockWorker<Object>() {
            @Override
            public Object invokeAfterLockAquire() {
                try {
                    System.out.println("执行方法！  -----  " + LocalDateTime.now());
                    //Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

        });

        return "hello world!";
    }

}
