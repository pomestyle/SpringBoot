package com.example.springbootlistenerdemo.listener;

import com.example.springbootlistenerdemo.bean.User;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @description: 自定义事件
 * @author: Administrator
 * @create: 2019-07-06 18:02
 **/
@Data
public class MyEvent extends ApplicationEvent {

    private User user;

    public MyEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}