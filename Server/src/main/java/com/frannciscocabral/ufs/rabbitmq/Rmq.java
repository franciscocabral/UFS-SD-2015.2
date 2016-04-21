/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frannciscocabral.ufs.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 *
 * @author Francisco Cabral
 */
public class Rmq {
    public static final String SERVER_QUEUE = "SERVER";

    public static Connection init() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("franciscocabral.com");
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory.newConnection();
    }

    public static void sendMessage(String queue, String message) {
        try {
            Connection connection = Rmq.init();
            Channel channel = connection.createChannel();
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish("", queue, null, message.getBytes());
            channel.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void receiveMessage(Consumer consumer) {
        try {
            Connection connection = Rmq.init();
            Channel channel = connection.createChannel();
            channel.queueDeclare(SERVER_QUEUE, false, false, false, null);
            channel.basicConsume(SERVER_QUEUE, true, consumer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void sendPublishment(String exchange, String message) {
        try {
            Connection connection = Rmq.init();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(exchange, "fanout");
            channel.basicPublish(exchange, "", null, message.getBytes());
            channel.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void receivePublishment(String exchange, Consumer consumer) {
        try {
            Connection connection = Rmq.init();
            Channel channel = connection.createChannel();
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, exchange, "");
            channel.basicConsume(queueName, true, consumer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
