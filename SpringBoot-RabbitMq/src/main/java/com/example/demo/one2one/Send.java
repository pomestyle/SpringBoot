package com.example.demo.one2one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;


/**
 * @description: 发送消息
 * @author: Administrator
 * @create: 2020-07-11 21:58
 **/
public class Send {

    public static final String QUEUE_NAME = "test_queue1";


    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        Channel channel = null;
        Connection connection = null;
        factory.setHost("localhost");
        //factory.setPort(5671);

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            //创建生命队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World! 111 ";
            //发送消息
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            factory.clone();
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
