/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frannciscocabral.ufs.utils;

import com.google.gson.Gson;

/**
 *
 * @author Fancisco
 */
public class Message {

    public String content;
    public String type;

    public Message() {}
    
    public Message(String content) {
        this.content = content;
        this.type = "none";
    }
    public Message(String type, String content) {
        this.content = content;
        this.type = type;
    }
    
    public static Message addUser(String name, String email, String password){
        String[] s = {name, email, password};
        Gson g = new Gson();
        return new Message("ADDUSER", g.toJson(s));
    }
    
    public static Message getUser(int userId){
        return new Message("GETUSER", Integer.toString(userId));
    }
    
    public static Message verifyUser(String email, String password){
        String[] s = {email, password};
        Gson g = new Gson();
        return new Message("VERIFYUSER", g.toJson(s));
    }
    
    public static Message getMessages(int userId, int contactId){
        String[] s = {Integer.toString(userId), Integer.toString(contactId)};
        Gson g = new Gson();
        return new Message("GETMESSAGES", g.toJson(s));
    }
    
    public static Message getContacts(int userId){
        return new Message("GETCONTACTS", Integer.toString(userId));
    }
    
    public static Message addContact(int userId, int contactId){
        String[] s = {Integer.toString(userId), Integer.toString(contactId)};
        Gson g = new Gson();
        return new Message("ADDCONTACT", g.toJson(s));
    }
    
    public static Message sendMessage(int userId, int contactId, String message){
        long unixTime = System.currentTimeMillis() / 1000L;
        String[] s = {Integer.toString(userId), Integer.toString(contactId), message, Long.toString(unixTime)};
        Gson g = new Gson();
        return new Message("SENDMESSAGE", g.toJson(s));
    }
    
    public String toJson(){
        Gson g = new Gson();
        return g.toJson(this);
    }
}
