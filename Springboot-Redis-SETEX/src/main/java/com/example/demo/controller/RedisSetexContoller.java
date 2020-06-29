package com.example.demo.controller;

import com.example.demo.redis.config.DistributedLockConfig;
import com.example.demo.redis.pojo.Lock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description: 基于redis setex 分布式锁
 * @author: Administrator
 * @create: 2020-05-01 22:28
 **/
@RestController
public class RedisSetexContoller {

    @Autowired
    DistributedLockConfig distributedLockConfig;


    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping("setIfAbsent")
    public String setIfAbsentTest() {
        return setIfAbsent();
    }

    //缓存击穿 分布式锁使用
    public String setIfAbsent() {
        //分布式锁 加锁  大量并发过来只有一个能拿到锁，然后其他并发线程进行自旋，等待第一个获取到分布式锁的线程进行 查询数据,存取到redis中,然后其他并发线程再获取锁直接从redis中获取数据.
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("keyL:loc", 1, 1000, TimeUnit.SECONDS);

        //拿到锁
        if (aBoolean) {

            //先查询缓存有没有数据
            if (false) {
                //没数据  查询数据
                String xml = "从数据库查询到的数据";
                if (!StringUtils.isEmpty(xml)) {
                    //加入缓存
                    redisTemplate.opsForValue().set("key:data", 123);
                } else {
                    //防止缓存击穿 将空串设置到redis中
                    redisTemplate.opsForValue().set("key:data", null);
                }

                //释放锁
                redisTemplate.delete("keyL:loc");

                return "ok";

            } else {

                //释放锁
                redisTemplate.delete("keyL:loc");

                //从缓存中获取
                return "ok";
            }


        } else {

            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //自旋获取锁
            return setIfAbsent();
        }
    }


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
