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
public class simpleChat {

    public static void main(String args[]) throws SocketException, UnknownHostException, IOException {
        Scanner s = new Scanner(System.in);
        System.out.println(".------------------------------------------------------------------------------.");
        System.out.println("| Simples exemplo de chat utilizando websocket, funcionando como rádio amador. |");
        System.out.println("'------------------------------------------------------------------------------'");
        System.out.print("Digite sua PORTA: ");
        int myPort = s.nextInt();
        s.nextLine();
        System.out.print("Digite o IP do remetente: ");
        String ip = s.nextLine();
        System.out.print("Digite a PORTA do remetente: ");
        int port = s.nextInt();

        DatagramSocket socket = new DatagramSocket(myPort);
        InetAddress addr = InetAddress.getByName(ip);

        System.out.println("");
        System.out.println(".------------------------------------------------------------------------------.");
        System.out.println("| Iniciando diálogo, digite CTRL+C para sair                                   |");
        System.out.println("'------------------------------------------------------------------------------'");
        String msg;
        
        s.nextLine();
        
        do {
            System.out.print("Você@"+myPort+": ");
            msg = s.nextLine();
            
            byte[] buffer = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addr, port);
            try {
                socket.send(packet);
            } catch (Exception e) {
                System.out.println("Não foi possível enviar mensagem para "+ip + "@" + port +", esperando resposta...");
            }

            if("!exit".equals(msg)) break;
            DatagramPacket responsePacket = new DatagramPacket(new byte[250], 250);
            socket.receive(responsePacket);

            byte[] bufferEmissor = responsePacket.getData();
            String msgEmissor = new String(bufferEmissor);

            System.out.println("\n"+ip + "@" + port + ": " + msgEmissor);
        } while (!"!exit".equals(msg));
        socket.close();
    }
}
