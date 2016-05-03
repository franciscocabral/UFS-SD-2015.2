package sd.fofocas;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Th on 03/05/2016.
 */
public class ConversaAdapeter implements Adapter {
    private Context contexto;
    private ArrayList<Conversa> lista;

    public ConversaAdapeter (Context contexto, ArrayList<Conversa> lista){
        this.contexto=contexto;
        this.lista=lista;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

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
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {
        final Conversa conversa = lista.get(posicao);
        final View layout;
        TextView nome;

        if (convertView == null && conversa instanceof Enviada) {
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.enviada, null);
        } else if(convertView == null && conversa instanceof Recebida){
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.recebida, null);
        } else {
            layout = convertView;
        }
        nome = (TextView) layout.findViewById(R.id.tvTexto);
        nome.setText(conversa.getTexto());


        return layout;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
