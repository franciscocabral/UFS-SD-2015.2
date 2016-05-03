package sd.fofocas;

import android.content.Context;

import java.text.SimpleDateFormat;


/**
 * Created by Th on 03/05/2016.
 */
abstract class Conversa {
    private String texto;
    private SimpleDateFormat data;
    private String usuario;
    private Context context;

    public Conversa(Context context, String texto, String usuario, SimpleDateFormat data){
        this.texto = texto;
        this.usuario = usuario;
        this.data = data;
        this.context = context;
    }

    public String getTexto(){
        return texto;
    }
}
