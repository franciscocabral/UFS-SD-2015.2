/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frannciscocabral.ufs.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Francisco Cabral
 */
public class rmqConfig {
    public static Connection init() throws IOException, TimeoutException{
            ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("franciscocabral.com");
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory.newConnection();
    }    
}
