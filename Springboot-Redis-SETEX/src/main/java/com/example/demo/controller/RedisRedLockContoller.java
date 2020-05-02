package com.example.demo.controller;

import com.example.demo.redis.config.DistributedLockConfig;
import com.example.demo.redis.pojo.Lock;
import com.example.demo.redis.redlock.AquiredLockWorker;
import com.example.demo.redis.redlock.RedisLocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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



    @RequestMapping("index0")
    public String index()throws Exception {

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        for (int i = 0; i < 50; i++) {
            scheduledExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
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
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });
        }
        scheduledExecutorService.shutdown();


        return "hello world!";
    }



    @Autowired
    RedisLocker redisLocker;

    @RequestMapping("test2")
    public void test2() throws Exception {
      /*  //计数1 对创建的每个线程进行计数，待线程都启动了，再进行抢锁
        CountDownLatch countDownLatchStart = new CountDownLatch(1);
        //5个线程都释放锁计数
        CountDownLatch doneSignal = new CountDownLatch(5);
        for (int i = 0; i < 50; i++) {
            //创建线程
            new Thread(new Worker(countDownLatchStart, doneSignal)).start();
        }
        //启动线程计数
        countDownLatchStart.countDown();
        //所有线程都执行完
        doneSignal.await();
        */

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        for (int i = 0; i < 50; i++) {
            scheduledExecutorService.execute(new Worker());
        }
        scheduledExecutorService.shutdown();
    }





    //任务
    class Worker implements Runnable {


        /*  private final CountDownLatch startSignal;
          private final CountDownLatch doneSignal;

          public  Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
              this.startSignal = startSignal;
              this.doneSignal = doneSignal;
          }*/
        public Worker() {

        }

        @Override
        public void run() {
            try {
                //等待所有线程都启动后，阻塞到这儿再开始在执行,再开始争抢锁
                // startSignal.await();
                redisLocker.lock("tizz1100", new AquiredLockWorker<Object>() {
                    @Override
                    public Object invokeAfterLockAquire() {
                        doTask();
                        return null;
                    }
                });
            } catch (Exception e) {
            }
        }

        void doTask() {
            System.out.println(Thread.currentThread().getName() + " ---------- " + LocalDateTime.now());
            System.out.println(Thread.currentThread().getName() + " start");
            Random random = new Random();
            int _int = random.nextInt(200);
            System.out.println(Thread.currentThread().getName() + " sleep " + _int + "millis");
           /* try {
                Thread.sleep(_int);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            System.out.println(Thread.currentThread().getName() + " end");
            //所有线程争抢完锁后计数
            //doneSignal.countDown();
        }

    }
}
