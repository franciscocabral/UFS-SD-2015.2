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

    public Mensagem(String texto, Date data, boolean enviada){
        this.texto = texto;
        this.data = (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")).format(data);
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
