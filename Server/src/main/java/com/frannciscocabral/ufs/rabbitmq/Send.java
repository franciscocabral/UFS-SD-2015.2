/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frannciscocabral.ufs.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Francisco Cabral
 */
public class Send {

    public static void main(String[] argv) throws java.io.IOException, TimeoutException {
        Connection connection = rmqConfig.init();
        Channel channel = connection.createChannel();
        
        String queue = "minhaFila";
        channel.queueDeclare(queue, false, false, false, null);
        String message = "minha benga!";
        channel.basicPublish("", queue, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
