package com.example.activemq.activemq.QueueAndTopic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 *
 **/
@Component
public class Consumer5 {

    @JmsListener(destination = "neo.queue", containerFactory = "queueListenerFactory")
    public void receiveQueue(String text) {
        System.out.println("Consumer5 queue msg : " + text);
    }

    @JmsListener(destination = "topic1", containerFactory = "topicListenerFactory")
    public void receiveTopic1(String text) {
        System.out.println("Consumer5 receiveTopic1 msg : " + text);
    }

    @JmsListener(destination = "topic1", containerFactory = "topicListenerFactory")
    public void receiveTopic2(String text) {
        System.out.println("Consumer5 receiveTopic2 msg : " + text);
    }
}