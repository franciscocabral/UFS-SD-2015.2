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
public class p2 {

    public static void main(String args[]) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket(5000);
        DatagramPacket packet = new DatagramPacket(new byte[40], 40);
        System.out.println("Esperando mensagem...");
        socket.receive(packet);
        System.out.println("Mensagem recebida:");
        InetAddress addrEmissor = packet.getAddress();
        int portEmissor = packet.getPort();
        byte[] bufferEmissor = packet.getData();
        String msg = new String(bufferEmissor);
        
        System.out.println(addrEmissor.getHostAddress()+":"+portEmissor+" - "+msg);
        
        String responseMsg = "mundo!";
        byte[] responseBuffer = responseMsg.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, addrEmissor, portEmissor);
        System.out.println("Enviando mensagem...");
        socket.send(responsePacket);
        System.out.println("Mensagem enviada!");
       
        socket.close();
    }
}
