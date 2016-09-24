/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lembretes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente extends Thread {

    private void newLembrete() throws IOException {
        Scanner s = new Scanner(System.in);
        System.out.print("Nome do remédio: ");
        this.dout.writeUTF(s.nextLine());
        System.out.print("Intervalo: ");
        this.dout.writeInt(s.nextInt());
        System.out.print("Comprimidos: ");
        this.dout.writeInt(s.nextInt());
    }

    public static class Reciver extends Thread {

        private DataInputStream din;

        Reciver(DataInputStream din) {
            this.din = din;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println(din.readUTF());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Cliente(Socket socket) throws IOException {
        this.socket = socket;
            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();

            this.din = new DataInputStream(this.in);
            this.dout = new DataOutputStream(this.out);
    }

    private Socket socket;
        private InputStream in;
        private OutputStream out;
        private DataInputStream din;
        private DataOutputStream dout;

    @Override
    public void run() {
        try{
        Scanner s = new Scanner(System.in);
        Reciver r = new Reciver(din);
        r.start();
        System.out.println("Escreva !new para adicionar um novo medicamento...");
        while (true) {
            String command = s.nextLine();
            if (command.equals("!new")) {
                this.newLembrete();
            }
        }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException {
//        Scanner s = new Scanner(System.in);
        Cliente cliente;

//        System.out.print("Digite o IP do remetente: ");
        String ip = "127.0.0.1";
//        System.out.print("Digite a PORTA do remetente: ");
        int port = 9000;
//        s.nextLine();

        System.out.println("Conectando...");
        cliente = new Cliente(new Socket(ip, port));
        System.out.println("Conectado!");

        cliente.start();

    }
}
