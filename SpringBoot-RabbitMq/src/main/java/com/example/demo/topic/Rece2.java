package com.example.demo.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description:
 * @author: Administrator
 * @create: 2020-07-12 17:55
 **/
public class Rece2 {

    //交换机
    private static final String EXCHANGE_NAME = "exchange_topic";
    //队列
    public static final String QUEUE_NAME = "router_queue_topic_del";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setVirtualHost("/test");
        factory.setUsername("test");
        factory.setPassword("test");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //绑定交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");


        //绑定队列到交换机  绑定del路由 key
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "iteme.del");


        //同一时刻只接受一条消息
        channel.basicQos(1);


        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] 删除redis缓存  '" + message + "'");
        };

        //自动确认消息
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}
