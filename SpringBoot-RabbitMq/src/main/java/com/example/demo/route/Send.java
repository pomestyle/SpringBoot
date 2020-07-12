package com.example.demo.route;

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
    private static final String EXCHANGE_NAME = "exchange_router";


    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setVirtualHost("/test");
        factory.setUsername("test");
        factory.setPassword("test");


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //绑定交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        //消息
      /*  String msg = "新增商品..... ";
        channel.basicPublish(EXCHANGE_NAME,"add",null,msg.getBytes());*/
        String msg = "删除商品..... ";
        channel.basicPublish(EXCHANGE_NAME,"del",null,msg.getBytes());
        System.out.println("发送的消息 ： " + msg);

        channel.close();
        connection.close();
    }
}