package com.example.demo.redis.pojo;

import lombok.Data;

/**
 * @description: 全局锁，包括锁的名称
 * @author: Administrator
 * @create: 2020-05-01 22:00
 **/
@Data
public class Lock {
    /**
     * key名
     */
    private String name;
    /**
     * value值
     */
    private String value;

    public Lock(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
