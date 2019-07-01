package com.example.activemq.activemq.Topic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

@Component
public class ProducerTopic {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Topic topic;

    public void sendTopic(String msg) {
        System.out.println(" 发送的消息是 :" + msg);
        this.jmsMessagingTemplate.convertAndSend(this.topic, msg);
    }
}