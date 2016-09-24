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
import java.util.Scanner;

/**
 *
 * @author aluno
 */
public class simpleChat {

    public static class Talk implements Runnable {

        private Socket socket;
        private String name;

        private Talk(Socket socket, String name) {
            this.socket = socket;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Scanner s = new Scanner(System.in);
                String msg;
                do{
                    OutputStream out = this.socket.getOutputStream();
                    byte[] buffer = new byte[250];
                    msg = s.nextLine();
                    buffer = (this.name+": "+msg).getBytes();
                    out.write(buffer);
                }while(!msg.equals("!exit"));

                this.socket.close();
            } catch (Exception e) {

            }
        }

    }

    public static class Listen implements Runnable {

        private Socket socket;

        private Listen(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                while(true){
                InputStream in = this.socket.getInputStream();
                byte[] buffer = new byte[250];
                int length = in.read(buffer);
                String msg = new String(buffer, 0, length);
                System.out.println(/*"Externo: " +*/ msg);
                }
            } catch (Exception e) {

            }
        }

    }

    public static void main(String args[]) throws IOException {
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
        Socket socket;

        s.nextLine();
        System.out.print("Digite seu Nome: ");
        String name = s.nextLine();
        if (opt == 1) {
            ServerSocket ss = new ServerSocket(7000);
            System.out.println("Esperando conexão...");
            socket = ss.accept();
            System.out.println("Conectado!");
            
        } else {
            System.out.print("Digite o IP do remetente: ");
            String ip = s.nextLine();
            System.out.print("Digite a PORTA do remetente: ");
            int port = s.nextInt();
            s.nextLine();

            System.out.println("Conectando...");
            socket = new Socket(ip, port);
            System.out.println("Conectado!");
        }

        System.out.println("");
        System.out.println(".------------------------------------------------------------------------------.");
        System.out.println("| Iniciando diálogo, digite !exit para sair.                                   |");
        System.out.println("'------------------------------------------------------------------------------'");

        Listen listen = new Listen(socket);
        Thread threadListen = new Thread(listen);
        threadListen.start();

        Talk talk = new Talk(socket,name);
        Thread threadTalk = new Thread(talk);
        threadTalk.start();

    }
}
