/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author aluno
 */
public class p1 {
    public static void main(String args[]) throws SocketException, UnknownHostException, IOException{
        DatagramSocket socket = new DatagramSocket(3000);
        String msg = "Olá";
        byte[] buffer = msg.getBytes();
        InetAddress addr = InetAddress.getByName("127.0.0.1");
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addr, 5000);
        System.out.println("Enviando mensagem...");
        socket.send(packet);
        System.out.println("Mensagem enviada!");
        System.out.println("Esperando resposta...");
        DatagramPacket responsePacket = new DatagramPacket(new byte[40], 40);
        socket.receive(responsePacket);
        System.out.println("Mensagem recebida:");
               
        InetAddress addrEmissor = responsePacket.getAddress();
        int portEmissor = responsePacket.getPort();
        byte[] bufferEmissor = responsePacket.getData();
        String msgEmissor = new String(bufferEmissor);
        
        System.out.println(addrEmissor.getHostAddress()+":"+portEmissor+" - "+msgEmissor);
        
        
        
        socket.close();
    }
}
