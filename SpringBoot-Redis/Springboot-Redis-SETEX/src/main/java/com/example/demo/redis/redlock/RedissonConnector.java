package com.example.demo.redis.redlock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @description: 获取RedissonClient连接类
 * @author: Administrator
 * @create: 2020-05-02 10:43
 **/
@Component
public class RedissonConnector {
    RedissonClient redisson;
    @PostConstruct
    public void init(){
        redisson = Redisson.create();
    }

    public RedissonClient getClient(){
        return redisson;
    }

}
