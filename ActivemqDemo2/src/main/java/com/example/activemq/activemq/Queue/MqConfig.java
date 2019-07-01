package com.example.activemq.activemq.Queue;


import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;
import javax.jms.Topic;


/**
 * 创建队列
 * 队列发送的消息，只能被一个消费者接收。
 */

@Configuration
public class MqConfig {
    @Bean
    public Queue queue() {
        return new ActiveMQQueue("neo.queue");
    }

    @Bean
    public Topic topic() {
        return new ActiveMQTopic("topic1");
    }


}
