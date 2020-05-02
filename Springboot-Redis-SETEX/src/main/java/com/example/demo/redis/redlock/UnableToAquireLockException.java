package com.example.demo.redis.redlock;

/**
 * @description: 异常类
 * @author: Administrator
 * @create: 2020-05-02 10:43
 **/
public class UnableToAquireLockException extends RuntimeException {

    public UnableToAquireLockException() {
    }

    public UnableToAquireLockException(String message) {
        super(message);
    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
