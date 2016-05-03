package sd.fofocas;

import android.content.Context;

/**
 * Created by Th on 03/05/2016.
 */
public class Amigo {
    private String nome;
    private int id;
    private Context context;


    public Amigo(String nome, int id, Context context){
        this.context=context;
        this.id=id;
        this.nome=nome;
    }

    public String getNome(){
        return nome;
    }

    public int getId(){
        return id;
    }

}
