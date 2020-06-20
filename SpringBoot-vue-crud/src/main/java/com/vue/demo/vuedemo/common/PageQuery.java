package com.vue.demo.vuedemo.common;

import lombok.Data;

/**
 * @description:
 * @author: Administrator
 * @create: 2020-06-20 17:28
 **/
@Data
public class PageQuery<T> {

    private Integer page = 1;
    private Integer pageSize = 10;

    private T object;

}
