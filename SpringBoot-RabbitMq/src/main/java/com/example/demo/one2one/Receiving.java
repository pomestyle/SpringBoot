package com.example.demo.one2one;

import com.rabbitmq.client.*;

import java.io.IOException;

import static com.example.demo.one2one.Send.QUEUE_NAME;


/**
 * @description: 点对点 消息生产消费
 * @author: Administrator
 * @create: 2020-07-11 21:57
 **/
public class Receiving {

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");


        while (true) {

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };
            //true 监听队列 有消息就获取 没有就阻塞  true表示自动确认消息
            channel.basicConsume(QUEUE_NAME, false, consumer);

        }


    }


}
