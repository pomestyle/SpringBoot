package com.example.demo.redis.redlock;

/**
 * @description: 获取锁后需要处理的逻辑
 * @author: Administrator
 * @create: 2020-05-02 10:41
 **/
public interface AquiredLockWorker<T> {
    T invokeAfterLockAquire() throws Exception;
}
