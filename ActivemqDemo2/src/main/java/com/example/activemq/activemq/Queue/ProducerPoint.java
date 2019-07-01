package com.example.activemq.activemq.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * @description:
 * @author: 消息生产者
 * @create: 创建一个消息的生产者：
 **/

@Component
public class ProducerPoint {

    //JmsMessagingTemplate 是 Spring 提供发送消息的工具类，使用 JmsMessagingTemplate 和创建好的 queue 对消息进行发送。
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    public void sendQueue(String msg) {
        System.out.println(" 发送消息为 :" + msg);
        this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
    }
}
