package sd.fofocas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Th on 03/05/2016.
 */
public class ConversaAdapter extends BaseAdapter {
    private Context contexto;
    private ArrayList<Mensagem> lista;

    public ConversaAdapter(Context contexto, ArrayList<Mensagem> lista){
        this.contexto=contexto;
        this.lista=lista;
    }

    @Override
    public int getCount(){
        return lista.size();
    }

    @Override
    public Object getItem(int posicao){
        return lista.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return 0;
    }


    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {
        final Mensagem mensagem = lista.get(posicao);
        final View layout;


        if (convertView == null && mensagem.isEnviada()) {
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.enviada, null);
        } else if (convertView == null && !mensagem.isEnviada()) {
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.recebida, null);
        } else {
            layout = convertView;
        }

        TextView texto = (TextView) layout.findViewById(R.id.tvTexto);
        texto.setText(mensagem.getTexto());


        return layout;
    }
}
