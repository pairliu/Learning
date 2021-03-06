package com.pairliu.learning.rabbitmq.helloworld;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
    private final static String QUEUE_NAME = "hello";
    
    public static void main(String[] args) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println(" [x] Sent '" + msg + "'");
        
        channel.close();
        connection.close();
    }

}
