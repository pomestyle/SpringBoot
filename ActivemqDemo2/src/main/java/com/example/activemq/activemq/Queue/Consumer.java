package com.example.activemq.activemq.Queue;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 创建消费者
 * <p>
 * 使用注解 @JmsListener(destination = "neo.queue")，
 * 表示此方法监控了名为 neo.queue 的队列。
 * 当队列 neo.queue 中有消息发送时会触发此方法的执行，text 为消息内容。
 **/
@Component
public class Consumer {

    @JmsListener(destination = "neo.queue")
    public void receiveQueue(String text) {
        System.out.println(" Consumer 接受到的消息是 : " + text);
    }
}
