package com.demo.redisson.controller;

import com.demo.redisson.config.RedisLockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 测试redission分布式锁
 * @author: Administrator
 * @create: 2020-06-30 22:22
 **/
@RestController
public class RedissonController {

    private static Logger log = LoggerFactory.getLogger(RedissonController.class);


    /**
     * 锁测试共享变量
     */
    private static Integer lockCount = 0;

    /**
     * 无锁测试共享变量
     */
    private  static Integer count = 0;

    /**
     * 模拟线程数
     */
    private static int threadNum = 20;

    /**
     * 模拟并发测试加锁和不加锁
     * @return
     */
    @GetMapping("/test")
    public void lock(){
        // 计数器
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,10,5,TimeUnit.SECONDS,new LinkedBlockingQueue<>(10000));

        for (int i = 0; i < 100; i ++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    //testCount();
                    testLockCount();
                }
            });
        }

        threadPoolExecutor.shutdown();

    }

    /**
     * 加锁测试
     */
    private void testLockCount() {
        String lockKey = "lock-test";
        try {
            // 加锁，设置超时时间2s
            RedisLockUtil.lock(lockKey,2, TimeUnit.SECONDS);
            lockCount++;
            log.info("lockCount值："+lockCount);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {
            // 释放锁
            RedisLockUtil.unlock(lockKey);
        }
    }

    /**
     * 无锁测试
     */
    private void testCount() {
        count++;
        log.info("count值："+count);
    }


    public class MyRunnable implements Runnable {
        /**
         * 计数器
         */
        final CountDownLatch countDownLatch;

        public MyRunnable(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                // 阻塞当前线程，直到计时器的值为0
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error(e.getMessage(),e);
            }
            // 无锁操作
            testCount();
            // 加锁操作
           testLockCount();
        }

    }

}

