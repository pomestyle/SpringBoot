package com.example.demo.redis.redlock;

/**
 * @description: 获取锁管理类
 * @author: Administrator
 * @create: 2020-05-02 10:42
 **/
public interface DistributedLocker {

    /**
     * 获取锁
     * @param resourceName  锁的名称
     * @param worker 获取锁后的处理类
     * @param <T>
     * @return 处理完具体的业务逻辑要返回的数据
     * @throws UnableToAquireLockException
     * @throws Exception
     */
    <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception;

    /**
     * 一般使用获取锁时 使用超时时间，防止死锁
     * @param resourceName
     * @param worker
     * @param lockTime
     * @param <T>
     * @return
     * @throws UnableToAquireLockException
     * @throws Exception
     */
    <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) throws UnableToAquireLockException, Exception;

}
