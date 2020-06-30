package com.example.rediscluster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class RedisClusterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisClusterApplication.class, args);
    }


}
