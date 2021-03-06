package com.example.demo.work;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description:
 * @author: Administrator
 * @create: 2020-07-12 16:19
 **/
public class Rece1 {


    public static String QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setVirtualHost("/test");
        factory.setUsername("test");
        factory.setPassword("test");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //同一时刻只能发送一个消息给消费者  那个消费者早消费完 早可以拿消息进行消费  能者多劳
        channel.basicQos(1);


        channel.queueDeclare(
                QUEUE_NAME, false, false,
                false, null
        );


        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //反馈消息
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        //false 表示不自动确认消息 需要手动反馈消息
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}


