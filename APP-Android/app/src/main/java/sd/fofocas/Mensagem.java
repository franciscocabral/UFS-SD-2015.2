package sd.fofocas;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Th on 03/05/2016.
 */
public class Mensagem {
    private String texto;
    private String data;
    private boolean enviada;

    public Mensagem(String texto, String data, boolean enviada){
        this.texto = texto;
        this.data = data;
        this.enviada = enviada;
    }

    public String getTexto(){
        return texto;
    }

    public String getData(){
        return data;
    }

    public boolean isEnviada(){
        return enviada;
    }
}
