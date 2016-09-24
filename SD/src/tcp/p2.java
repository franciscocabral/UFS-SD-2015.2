/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author aluno
 */
public class p2 {
    public static void main(String args[]) throws SocketException, UnknownHostException, IOException {
        Scanner s = new Scanner(System.in);
        
        ServerSocket ss = new ServerSocket(7000);
        System.out.println("Esperando conexão...");
        Socket socket = ss.accept();
        System.out.println("Conectado!");

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        
        byte[] buffer = new byte[250];
        
        System.out.println("\nRecebendo mensagem...");
        int length = in.read(buffer);
        String msg = new String(buffer, 0, length);
        System.out.println(msg);
        System.out.println("Mensagem recebida!");
        
        
        System.out.println("\nEnviando mensagem...");
        System.out.print("Digite uma msg: ");
        msg = s.nextLine();
        buffer = msg.getBytes();
        out.write(buffer);
        System.out.println("Mensagem enviada!");
        
        socket.close();
        ss.close();
    }
}
