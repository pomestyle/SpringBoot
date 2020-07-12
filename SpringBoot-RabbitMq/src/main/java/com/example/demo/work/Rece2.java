package com.example.demo.work;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description:
 * @author: Administrator
 * @create: 2020-07-12 16:19
 **/
public class Rece2 {


        public static String QUEUE_NAME = "test_queue";

        public static void main(String[] args) throws IOException, TimeoutException {

            ConnectionFactory factory = new ConnectionFactory();

            factory.setHost("localhost");
            factory.setVirtualHost("/test");
            factory.setUsername("test");
            factory.setPassword("test");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            //同一时刻只能发送一个消息给消费者
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
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };



            channel.basicConsume(QUEUE_NAME, false, consumer);
        }
    }
