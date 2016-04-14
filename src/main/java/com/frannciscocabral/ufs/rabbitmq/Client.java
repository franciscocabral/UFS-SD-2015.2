/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frannciscocabral.ufs.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Francisco Cabral
 */
public class Client {

    public static void main(String[] argv) throws java.io.IOException, TimeoutException {
        Scanner s = new Scanner(System.in);
        Connection connection = rmqConfig.init();
        Channel channel = connection.createChannel();
        Channel channel2 = connection.createChannel();

        System.out.print("User: ");
        String user = s.next();
        String msg = "";
        String queue = "";
        String message;
        s.nextLine();
        while (!msg.equals("!exit")) {
            System.out.print(queue+">>");
            msg = s.nextLine();
            if(msg.equals("!exit")) break;
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            if (msg.charAt(0) == '@') {
                queue = msg.split(" ")[0].substring(1).toUpperCase();
                message = msg.substring(queue.length() + 1);
            } else {
                message = msg;
            }
            message = user+" - "+dateFormat.format(date)+": "+message;
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish("", queue, null, message.getBytes());
            
            channel2.queueDeclare(user.toUpperCase(), false, false, false, null);

            Consumer consumer = new DefaultConsumer(channel2) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(message);
                }
            };
            channel2.basicConsume(user.toUpperCase(), true, consumer);
        }

        channel.close();
        channel2.close();
        connection.close();
    }
}
