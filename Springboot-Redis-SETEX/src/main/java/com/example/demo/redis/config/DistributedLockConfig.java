package com.example.demo.redis.config;

import com.example.demo.redis.pojo.Lock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;


/**
 * @description: 分布式锁配置类
 * @author: Administrator
 * @create: 2020-05-01 22:05
 **/
@Slf4j
@Component
public class DistributedLockConfig {


    /**
     * 单个业务持有锁的时间30s，防止死锁
     */
    private final static long LOCK_EXPIRE = 30 * 1000L;
    /**
     * 默认30ms尝试一次
     */
    private final static long LOCK_TRY_INTERVAL = 30L;
    /**
     * 默认尝试20s
     */
    private final static long LOCK_TRY_TIMEOUT = 20 * 1000L;


    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 尝试获取全局锁
     *
     * @param lock 锁的名称
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(Lock lock) {
        return getLock(lock, LOCK_TRY_TIMEOUT, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
    }

    /**
     * 尝试获取全局锁
     * SETEX：可以设置超时时间
     *
     * @param lock    锁的名称
     * @param timeout 获取超时时间 单位ms
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(Lock lock, long timeout) {
        return getLock(lock, timeout, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
    }

    /**
     * 尝试获取全局锁
     *
     * @param lock        锁的名称
     * @param timeout     获取锁的超时时间
     * @param tryInterval 多少毫秒尝试获取一次
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(Lock lock, long timeout, long tryInterval) {
        return getLock(lock, timeout, tryInterval, LOCK_EXPIRE);
    }

    /**
     * 尝试获取全局锁
     *
     * @param lock           锁的名称
     * @param timeout        获取锁的超时时间
     * @param tryInterval    多少毫秒尝试获取一次
     * @param lockExpireTime 锁的过期
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(Lock lock, long timeout, long tryInterval, long lockExpireTime) {
        return getLock(lock, timeout, tryInterval, lockExpireTime);
    }


    /**
     * 操作redis获取全局锁
     *
     * @param lock           锁的名称
     * @param timeout        获取的超时时间
     * @param tryInterval    多少ms尝试一次
     * @param lockExpireTime 获取成功后锁的过期时间
     * @return true 获取成功，false获取失败
     */
    public boolean getLock(Lock lock, long timeout, long tryInterval, long lockExpireTime) {

        try {
            if (StringUtils.isEmpty(lock.getName()) || StringUtils.isEmpty(lock.getValue())) {
                return false;
            }
            long startTime = System.currentTimeMillis();
            do {
                if (!redisTemplate.hasKey(lock.getName())) {
                    ValueOperations<String, String> ops = redisTemplate.opsForValue();
                    ops.set(lock.getName(), lock.getValue(), lockExpireTime, TimeUnit.MILLISECONDS);
                    return true;
                } else {
                    //存在锁
                    log.info("lock is exist!！！");
                }

                //尝试超过了设定值之后直接跳出循环
                if (System.currentTimeMillis() - startTime > timeout) {
                    return false;
                }

                //每隔多长时间尝试获取
                Thread.sleep(tryInterval);
            }
            while (redisTemplate.hasKey(lock.getName()));
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * 获取锁
     * SETNX(SET If Not Exists)：当且仅当 Key 不存在时，则可以设置，否则不做任何动作。
     */
    public Boolean getLockNoTime(Lock lock) {
        if (!StringUtils.isEmpty(lock.getName())) {
            return false;
        }

        // setIfAbsent 底层封装命令 是 setNX()
        boolean falg = redisTemplate.opsForValue().setIfAbsent(lock.getName(), lock.getValue());

        return false;
    }

    /**
     * 释放锁
     */
    public void releaseLock(Lock lock) {
        if (!StringUtils.isEmpty(lock.getName())) {
            redisTemplate.delete(lock.getName());
        }
    }


    /**
     * setIfAbsent()
     */
    public void lock(){

        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("key1", "value", 2000, TimeUnit.SECONDS);

        long ls = LocalDate.now().toEpochDay() + 5000;

        while (!aBoolean){
            aBoolean = redisTemplate.opsForValue().setIfAbsent("key1", "value", 2000, TimeUnit.SECONDS);

            long ld = LocalDate.now().toEpochDay();
            //超时或者抢到锁
            if(aBoolean || ld > ls ){
                redisTemplate.delete("key1");
                return;
            }
        }

    }


}
