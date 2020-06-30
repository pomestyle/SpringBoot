package com.demo.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 配置类
 * @author: Administrator
 * @create: 2020-06-30 22:41
 **/
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        //单机配置
        config.useSingleServer().setAddress("redis://"+host+":"+port);



        //集群配置
        //config.useClusterServers().addNodeAddress(".... 可变参数 .");

/*
        //主从
        config.useMasterSlaveServers()
                .setMasterAddress("主节点配置")
                .addSlaveAddress("从节点配置 可变参数");
        //哨兵配置
        config.useSentinelServers().addSentinelAddress("哨兵配置地址 可变参数")
                .setMasterName("主库地址")
                .setTimeout(50000)
                .setMasterConnectionPoolSize(10)
                .setSlaveConnectionPoolSize(5);

        */


        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
