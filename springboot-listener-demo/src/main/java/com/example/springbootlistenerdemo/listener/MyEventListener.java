package com.example.springbootlistenerdemo.listener;

import com.example.springbootlistenerdemo.bean.User;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @description: 自定义监听器，监听 MyEvent 事件
 * @author: Administrator
 * @create: 2019-07-06 18:03
 **/
@Component
public class MyEventListener implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        // 把事件中的信息获取到
        User user = myEvent.getUser();
        // 处理事件，实际项目中可以通知别的微服务或者处理其他逻辑等
        System.out.println("用户名：" + user.getName());
        System.out.println("密码：" + user.getPass());

    }
}