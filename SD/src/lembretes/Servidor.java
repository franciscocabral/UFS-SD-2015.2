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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor extends Thread {

    public static class Connection extends Thread {

        private ArrayList<Lembrete> lembretes;
        private Socket socket;
        private InputStream in;
        private OutputStream out;
        private DataInputStream din;
        private DataOutputStream dout;

        Connection(Socket socket) throws IOException {
            this.socket = socket;
            this.lembretes = new ArrayList<Lembrete>();

            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();

            this.din = new DataInputStream(this.in);
            this.dout = new DataOutputStream(this.out);
        }

        private Lembrete newLembrete() throws IOException {
            String remedio = din.readUTF();
            int intervalo = din.readInt();
            int comprimidos = din.readInt();
            return new Lembrete(remedio, intervalo, comprimidos, this.dout);
        }

        @Override
        public void run() {
            try {
                String command;
                while (true) {
                    command = din.readUTF();
                    if (command.equals("!new")) {
                        Lembrete l = newLembrete();
                        this.lembretes.add(l);
                        l.start();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }

    private ArrayList<Connection> conections;

    public ArrayList<Connection> getLembretes() {
        return conections;
    }

    public void setLembretes(ArrayList<Connection> connections) {
        this.conections = connections;
    }

    @Override
    public void run() {
        ServerSocket ss;
        try {
            ss = new ServerSocket(9000);
            this.conections = new ArrayList<Connection>();
            while (true) {
                Socket socket = ss.accept();
                Connection c = new Connection(socket);
                this.conections.add(c);
                c.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException {
        Servidor servidor = new Servidor();
        servidor.start();
    }
}
