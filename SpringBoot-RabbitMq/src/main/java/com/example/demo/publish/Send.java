package com.example.demo.publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * @description:
 * @author: Administrator
 * @create: 2020-07-12 17:53
 **/
public class Send {
    //交换机
    private static final String EXCHANGE_NAME = "exchange_logs";


    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setVirtualHost("/test");
        factory.setUsername("test");
        factory.setPassword("test");


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //绑定交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        //消息
        String msg = "订单更新..... ";
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        System.out.println("发送的消息 ： " + msg);

        channel.close();
        connection.close();
    }
}