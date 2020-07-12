package com.example.demo.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description:
 * @author: Administrator
 * @create: 2020-07-12 16:19
 **/
public class Send {


        public static String QUEUE_NAME = "test_queue";

        public static void main(String[] args) throws IOException, TimeoutException {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setVirtualHost("/test");
            factory.setUsername("test");
            factory.setPassword("test");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(
                    QUEUE_NAME, false, false,
                    false, null
            );

            //发送消息
            for (int i = 0; i < 50; i++) {
                String message = "message: " + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(message);
                try {
                    Thread.sleep(i * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            channel.close();
            connection.close();
        }
}
