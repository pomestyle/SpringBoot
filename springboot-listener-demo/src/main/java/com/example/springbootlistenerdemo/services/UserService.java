package com.example.springbootlistenerdemo.services;

import com.example.springbootlistenerdemo.bean.User;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @create: 2019-07-06 17:22
 **/
@Service
public class UserService {
    /**
     * 获取用户信息
     * @return
     */
    public User getUser() {
        // 实际中会根据具体的业务场景，从数据库中查询对应的信息
        return new User(1L, "tizzy", "123456");
    }
}
