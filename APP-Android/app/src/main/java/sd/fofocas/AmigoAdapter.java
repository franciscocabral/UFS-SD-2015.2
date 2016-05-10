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
public class AmigoAdapter extends BaseAdapter {
    private Context contexto;
    private ArrayList<Amigo> lista;

    public AmigoAdapter (Context contexto, ArrayList<Amigo> lista){
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
        final Amigo amigo = lista.get(posicao);
        final View layout;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.amigo, null);
        } else {
            layout = convertView;
        }

        TextView nome = (TextView) layout.findViewById(R.id.tvAmigo);
        nome.setText(amigo.getNome());


        return layout;
    }
}
