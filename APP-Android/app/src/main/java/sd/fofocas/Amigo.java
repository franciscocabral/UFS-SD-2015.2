package sd.fofocas;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Th on 03/05/2016.
 */
public class Amigo {
    private String nome;
    private ArrayList<Mensagem> mensagens = new ArrayList<>();

    public Amigo(String nome){
        this.nome=nome.toUpperCase();
    }

    public String getNome(){
        return nome;
    }

    public void addMensagem(Mensagem mensagem){
        mensagens.add(mensagem);

    }

    public ArrayList<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(ArrayList<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }
}
