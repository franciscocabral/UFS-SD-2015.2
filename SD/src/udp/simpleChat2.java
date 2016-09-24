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
import java.util.Scanner;

/**
 *
 * @author aluno
 */
public class simpleChat2 {
    
    public static void main(String args[]) throws SocketException, UnknownHostException, IOException {
        Scanner s = new Scanner(System.in);
        System.out.println(".------------------------------------------------------------------------------.");
        System.out.println("| Simples exemplo de chat utilizando websocket, funcionando como rádio amador. |");
        System.out.println("'------------------------------------------------------------------------------'");
        System.out.println("");
        System.out.println(".------------------------------------------------------------------------------.");
        System.out.println("| 1) Iniciar servidor                                                          |");
        System.out.println("| 2) Conectar-se com alguem                                                    |");
        System.out.println("'------------------------------------------------------------------------------'");
        System.out.print("Digite sua opção: ");
        int opt = s.nextInt();
        
        DatagramSocket socket = new DatagramSocket();
        int myPort = socket.getLocalPort();
        DatagramPacket responsePacket = new DatagramPacket(new byte[250], 250);
        
        String msg;
        String ip;
        int port;
        InetAddress addr;
        
        if(opt == 1){
            System.out.println(".------------------------------------------------------------------------------.");
            System.out.println("| Servidor de chat iniciado. Escutando na porta "+myPort+", digite CTRL+C para sair |");
            System.out.println("'------------------------------------------------------------------------------'");
            socket.receive(responsePacket);
            port = responsePacket.getPort();
            addr = responsePacket.getAddress();
            ip = addr.getHostAddress();
            byte[] bufferEmissor = responsePacket.getData();
            String msgEmissor = new String(bufferEmissor);

            System.out.println(ip + "@" + port + ": " + msgEmissor);
        }else{
            s.nextLine();
            System.out.print("Digite o IP do remetente: ");
            ip = s.nextLine();
            System.out.print("Digite a PORTA do remetente: ");
            port = s.nextInt();
            addr = InetAddress.getByName(ip);
            System.out.println("");
            System.out.println(".------------------------------------------------------------------------------.");
            System.out.println("| Iniciando diálogo, digite !exit para sair.                                   |");
            System.out.println("'------------------------------------------------------------------------------'");
        }
        
        s.nextLine();
        
        do {
            msg = "";
            byte[] bufferEmissor = new byte[250];
            responsePacket = new DatagramPacket(new byte[250], 250);
            
            System.out.print("Você@"+myPort+": ");
            msg = s.nextLine();
            byte[] buffer = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addr, port);
            socket.send(packet);
            if("!exit".equals(msg)) break;
            
            socket.receive(responsePacket);

            bufferEmissor = responsePacket.getData();
            String msgEmissor = new String(bufferEmissor);

            System.out.println(ip + "@" + port + ": " + msgEmissor);
        } while (!"!exit".equals(msg));
        socket.close();
    }
}
