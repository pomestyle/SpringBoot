package com.example.demo.controller;

import com.example.demo.redis.config.DistributedLockConfig;
import com.example.demo.redis.pojo.Lock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
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

        //查询到的数据
        String data = "";

        //分布式锁
        String key = "keyL:loc:" + "sku";

        //缓存中没有
        if (true) {

            //如果缓存中没有，查询mysql
            if (StringUtils.isEmpty(null)) {
                //没数据  查询数据
                String xml = "从数据库查询到的数据";
                data = xml;

                //设置一个token锁 防止第一个线程请求进来，还在查询数据库（数据库查询超时）的情况下，第二个请求进来删除了第一个请求的key
                // 必须是当前线程释放当前线程的锁
                String uuidLock = UUID.randomUUID().toString();

                //设置分布式锁
                //分布式锁 加锁  大量并发过来只有一个能拿到锁，然后其他并发线程进行自旋，等待第一个获取到分布式锁的线程进行 查询数据,存取到redis中,然后其他并发线程再获取锁直接从redis中获取数据.
                Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(key, uuidLock, 5 * 1000, TimeUnit.SECONDS);

                if (aBoolean != null && aBoolean) {
                    //设置成功，在10秒的过期时间内访问数据库
                    //mysql查询结果存入redis
                    if (!StringUtils.isEmpty(xml)) {
                        //加入缓存
                        redisTemplate.opsForValue().set("key:data", 123);
                    } else {
                        //防止缓存击穿 将空串设置到redis中
                        redisTemplate.opsForValue().set("key:data", null);
                    }



                    //todo 使用lua脚本释放锁 待写...

                    //将分布锁释放
                    String tokenKey = (String) redisTemplate.opsForValue().get(key);

                    // 用token确认删除的是自己的sku的锁
                    if (StringUtils.isEmpty(tokenKey) && uuidLock.equals(tokenKey)) {
                        // --------------------------  如果redis中 tokenKey 获取到了，在删除之前刚好失效了，此时这里另外一个或多个线程进来进行业务逻辑操作，会出问题--------------------------
                        //这里释放锁需要原子性操作, 解决办法： 利用lua脚本 在redis中查询 tokenLock 锁的时候 如果发现了就删除。
                        //释放锁
                        redisTemplate.delete("keyL:loc");
                    }

                }


            } else {

                //自旋获取锁
                return setIfAbsent();
            }

        } else {
            //缓存中有数据 直接返回

            data = "缓存中有数据直接返回！";
        }
        return data;
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
