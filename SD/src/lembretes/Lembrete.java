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

public class Lembrete extends Thread {

    private Socket socket;
    private String remedio;
    private int intervalo;
    private int comprimidos;
    private DataOutputStream dout;

    Lembrete(String remedio, int intervalo, int comprimidos, DataOutputStream dout) {
        this.remedio = remedio;
        this.intervalo = intervalo;
        this.comprimidos = comprimidos;
        this.dout = dout;
    }

    Lembrete(Socket socket) throws IOException {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (this.comprimidos > 0) {
                dout.writeUTF("Tomar 1 comprimido de " + remedio);
                comprimidos--;
                Thread.sleep((long) (intervalo * 1000));
            }
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "{" + this.remedio + ", " + intervalo + ",  " + comprimidos + "}";
    }
}
