package com.example.rediscluster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisClusterApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public  void test1(){
        redisTemplate.opsForValue().set("test","test");
    }


}
