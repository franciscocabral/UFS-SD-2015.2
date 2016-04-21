/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frannciscocabral.ufs;

import com.frannciscocabral.ufs.rabbitmq.Receiver;
import com.frannciscocabral.ufs.rabbitmq.Rmq;
import com.frannciscocabral.ufs.utils.Message;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Fancisco
 */
public class Main {
    public static void main(String[] argv) throws NoSuchAlgorithmException {
        new Receiver().start();
        
        Message m = new Message("Olá como você está?");
        Rmq.sendMessage(Rmq.SERVER_QUEUE, m.toJson());
        
        MessageDigest md = MessageDigest.getInstance("MD5");
        String password = new String(md.digest("123456".getBytes()));
        
        m = Message.addUser("Teste", "teste@teste.com.br", password);
        Rmq.sendMessage(Rmq.SERVER_QUEUE, m.toJson());
    }
}
