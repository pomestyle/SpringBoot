package com.example.mongodbdemo.demo.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import sun.dc.pr.PRError;

/**
 * @description:
 * @author: Administrator
 * @create: 2019-07-02 19:53
 **/
@Data
@AllArgsConstructor
public class User {
    private String userName;
    private String password;
}
