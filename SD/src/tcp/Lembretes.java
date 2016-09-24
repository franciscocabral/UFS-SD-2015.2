/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class Lembretes {
    public static class Lembrete implements Runnable{

        private String remedio;
        private float intervalo;
        private int comprimidos;
        
        private Lembrete(String remedio, float intervalo, int comprimidos) {
            this.remedio = remedio;
            this.intervalo = intervalo;
            this.comprimidos = comprimidos;
        }
        
        @Override
        public void run() {
            while(this.comprimidos > 0){
                System.out.println("TRIIIIMM!!! TRIIIIMMM!!!!");
                System.out.println("Hora de tomar remedinho!");
                System.out.println("Tomar 1 comprimido de "+remedio);
                comprimidos--;
                try {
                    Thread.sleep((long)(intervalo*1000));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
    }
    
    public static void main(String args[]) throws IOException {
        Lembrete r1 = new Lembrete("Dora aventureira", 8f, 10);
        Lembrete r2 = new Lembrete("Para que você ta mal", 12f, 12);
        Lembrete r3 = new Lembrete("Nem mensiono sua linda", 4f, 6);
        
        
        Thread tr1 = new Thread(r1);
        Thread tr2 = new Thread(r2);
        Thread tr3 = new Thread(r3);
        tr1.start();
        tr2.start();
        tr3.start();
        
    }
    
}
