/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author aluno
 */
public class p1 {
    public static void main(String args[]) throws SocketException, UnknownHostException, IOException {
        Scanner s = new Scanner(System.in);
        
        System.out.println("Conectando...");
        Socket socket = new Socket("localhost",7000);
        System.out.println("Conectado!");
        
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        
        System.out.println("\nEnviando mensagem...");
        System.out.print("Digite uma msg: ");
        String msg = s.nextLine();
        byte[] buffer = msg.getBytes();
        out.write(buffer);
        System.out.println("Mensagem enviada!");
        
        buffer = new byte[250];
        
        System.out.println("\nRecebendo mensagem...");
        int length = in.read(buffer);
        msg = new String(buffer, 0, length);
        
        System.out.println(msg);
        System.out.println("Mensagem recebida!");
        socket.close();
        
    }
}
