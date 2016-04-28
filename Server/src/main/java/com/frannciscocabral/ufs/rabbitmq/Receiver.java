/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frannciscocabral.ufs.rabbitmq;

import com.frannciscocabral.ufs.utils.Message;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Francisco Cabral
 */
public class Receiver extends Thread {
    
    public static void addUser(byte[] body) throws UnsupportedEncodingException, Exception{
        Gson g = new Gson();
        Message m = g.fromJson(new String(body, "UTF-8"), Message.class); 
        String[] data = g.fromJson(m.content, String[].class);
        String name = data[0];
        String email = data[1];
        String password = data[2];
        System.out.println(data[0]+" "+data[1]+" "+data[2]);
        throw new Exception("Not implemented yet");
    }
    
    public static void getUser(byte[] body) throws UnsupportedEncodingException, Exception{
        Gson g = new Gson();
        Message m = g.fromJson(new String(body, "UTF-8"), Message.class); 
        String userId = m.content;
        System.out.println(m.content);
        throw new Exception("Not implemented yet");
    }
    public static void verifyUser(byte[] body) throws UnsupportedEncodingException, Exception{
        Gson g = new Gson();
        Message m = g.fromJson(new String(body, "UTF-8"), Message.class); 
        String[] data = g.fromJson(m.content, String[].class);
        String email = data[0];
        String password = data[1];
        System.out.println(data[0]+" "+data[1]);
        throw new Exception("Not implemented yet");
    }
    
    public static void getMessages(byte[] body) throws UnsupportedEncodingException, Exception{
        Gson g = new Gson();
        Message m = g.fromJson(new String(body, "UTF-8"), Message.class); 
        String[] data = g.fromJson(m.content, String[].class);
        String userId = data[0];
        String contactId = data[1];
        System.out.println(data[0]+" "+data[1]);
        throw new Exception("Not implemented yet");
    }
    
    public static void getContacts(byte[] body) throws UnsupportedEncodingException, Exception{
        Gson g = new Gson();
        Message m = g.fromJson(new String(body, "UTF-8"), Message.class); 
        String userId = m.content;
        System.out.println(m.content);
        throw new Exception("Not implemented yet");
    }
    
    public static void addContact(byte[] body) throws UnsupportedEncodingException, Exception{
        Gson g = new Gson();
        Message m = g.fromJson(new String(body, "UTF-8"), Message.class); 
        String[] data = g.fromJson(m.content, String[].class);
        String userId = data[0];
        String contactId = data[1];
        System.out.println(data[0]+" "+data[1]);
        throw new Exception("Not implemented yet");
    }
    
    public static void sendMessage(byte[] body) throws UnsupportedEncodingException, Exception{
        Gson g = new Gson();
        Message m = g.fromJson(new String(body, "UTF-8"), Message.class); 
        String[] data = g.fromJson(m.content, String[].class);
        String userId = data[0];
        String contactId = data[1];
        String message = data[2];
        String unixtime = data[3];
        System.out.println(data[0]+" "+data[1]+" "+data[2]+" "+data[3]);
        throw new Exception("Not implemented yet");
    }
    
    @Override
    public void run() {
        try {
            Consumer consumer = new DefaultConsumer(Rmq.init().createChannel()) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    
                    Gson g = new Gson();
                    Message m = g.fromJson(new String(body, "UTF-8"), Message.class);
                    System.out.println(" [x] Received: '" + new String(body) + "'");
                    try{
                        switch(m.type){
                            case "ADDUSER": Receiver.addUser(body); break;
                            case "GETUSER": Receiver.getUser(body); break;
                            case "VERIFYUSER": Receiver.verifyUser(body); break;
                            case "GETMESSAGES": Receiver.getMessages(body); break;
                            case "GETCONTACTS": Receiver.getContacts(body); break;
                            case "ADDCONTACT": Receiver.addContact(body); break;
                            case "SENDMESSAGE": Receiver.sendMessage(body); break;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };
            Rmq.receiveMessage(consumer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
