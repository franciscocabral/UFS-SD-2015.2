package com.chatt.demo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Th on 19/04/2016.
 */
public class RabbitMQConfig {
    static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("franciscocabral.com");
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory.newConnection();
    }

    static void sendMessage(String queue, String message){
        try {
            Connection connection = RabbitMQConfig.getConnection();
            Channel channel = connection.createChannel();
            //UserList.user.getUsername()
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish(message, queue, null, message.getBytes());

            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static void getMessages(String queue){
        try {
            Connection connection = RabbitMQConfig.getConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(queue, false, false, false, null);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                }
            };
            channel.basicConsume(queue, true, consumer);
            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
