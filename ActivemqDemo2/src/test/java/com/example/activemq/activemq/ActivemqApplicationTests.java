package com.example.activemq.activemq;

import com.example.activemq.activemq.Queue.ProducerPoint;
import com.example.activemq.activemq.Topic.ProducerTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivemqApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Autowired
    private ProducerPoint producer;

    @Autowired
    private ProducerTopic topic;


    // 生产者- 消费者 p2p  一个消息只能被一个消费者消费

    //当有多个消费者监听一个队列时，消费者会自动均衡负载的接收消息，并且每个消息只能有一个消费者所接收。
    @Test
    public void test1() {
        for (int i = 0; i < 3; i++) {
            producer.sendQueue(" 你好。。。");
        }

        /*

            发送消息为 : 你好。。。
            发送消息为 : 你好。。。
            Consumer2  接受到的消息是 :  你好。。。
            Consumer 接受到的消息是 :  你好。。。
            发送消息为 : 你好。。。
            Consumer2  接受到的消息是 :  你好。。。



         */
    }

    // 生产者- 消费者 topic
    @Test
    public void test2() {
        producer.sendQueue(" 你好。。。");
        topic.sendTopic(" 你们好。。。");
    }


}
