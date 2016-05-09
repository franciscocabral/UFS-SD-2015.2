package sd.fofocas;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Th on 03/05/2016.
 */
public class Amigo {
    private String nome;
    private int id;
    private ArrayList<Mensagem> mensagens = new ArrayList<>();

    public Amigo(String nome, int id){
        this.id=id;
        this.nome=nome;
    }

    public String getNome(){
        return nome;
    }

    public int getId(){
        return id;
    }

    public void addMensagem(){

    }

}
