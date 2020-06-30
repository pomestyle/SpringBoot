package com.example.sentinelredis;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;

/**
 * @author zhujinmin6
 */
@Configuration
@EnableAutoConfiguration
@EnableCaching //加上这个注解是的支持缓存注解
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout = 0;

    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.sentinel.nodes}")
    private String redisNodes;

    @Value("${spring.redis.sentinel.master}")
    private String master;


    /**
     * redis哨兵配置
     * @return
     */
    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration(){
        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
        String[] host = redisNodes.split(",");
        for(String redisHost : host){
            String[] item = redisHost.split(":");
            String ip = item[0];
            String port = item[1];
            configuration.addSentinel(new RedisNode(ip, Integer.parseInt(port)));
        }
        configuration.setMaster(master);
        return configuration;
    }

    /**
     * 连接redis的工厂类
     *
     * @return
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory(redisSentinelConfiguration());
        factory.setHostName(host);
        factory.setPort(port);
        factory.setTimeout(timeout);
        factory.setPassword(password);
        factory.setDatabase(database);
        return factory;
    }

    /**
     * 配置RedisTemplate
     * 设置添加序列化器
     * key 使用string序列化器
     * value 使用Json序列化器
     * 还有一种简答的设置方式，改变defaultSerializer对象的实现。
     *
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        //StringRedisTemplate的构造方法中默认设置了stringSerializer
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        //设置开启事务
        template.setEnableTransactionSupport(true);
        //set key serializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        template.setConnectionFactory(jedisConnectionFactory());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 设置RedisCacheManager
     * 使用cache注解管理redis缓存
     *
     * @return
     */
    @Override
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
        return redisCacheManager;
    }

    /**
     * 自定义生成redis-key
     *
     * @return
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName()).append(".");
                sb.append(method.getName()).append(".");
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                System.out.println("keyGenerator=" + sb.toString());
                return sb.toString();
            }
        };
    }
}