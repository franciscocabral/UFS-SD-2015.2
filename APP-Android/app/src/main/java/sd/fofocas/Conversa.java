package sd.fofocas;

import android.content.Context;

import java.text.SimpleDateFormat;


/**
 * Created by Th on 03/05/2016.
 */
public class Conversa {
    private String texto;
    private SimpleDateFormat data;
    private String usuario;
    private Context context;
    private boolean enviada;

    public Conversa(Context context, String texto, String usuario, SimpleDateFormat data,boolean enviada){
        this.texto = texto;
        this.usuario = usuario;
        this.data = data;
        this.context = context;
        this.enviada = enviada;
    }

    public String getTexto(){
        return texto;
    }

    public String getUsuario(){
        return usuario;
    }

    public SimpleDateFormat getData(){
        return data;
    }

    public boolean isEnviada(){
        return enviada;
    }
}
