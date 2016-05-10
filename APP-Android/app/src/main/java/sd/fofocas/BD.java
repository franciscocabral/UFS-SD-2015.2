package sd.fofocas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Th on 10/05/2016.
 */
public class BD {
    private SQLiteDatabase bd;

    public BD(Context contexto){
        BDCore auxBd = new BDCore(contexto);
        bd = auxBd.getWritableDatabase();
    }

    public void inserir(Mensagem mensagem, String amigo){
        ContentValues valores = new ContentValues();
        valores.put("amigo", amigo);
        valores.put("mensagem", mensagem.getTexto());
        valores.put("data", mensagem.getData());
        valores.put("enviada", mensagem.isEnviada());

        bd.insert("mensagem",null,valores);
    }

    public ArrayList<String> buscar(){
        ArrayList<String> amigos = new ArrayList<String>();
        String[] colunas = new String[]{"_id","amigo","mensagem","data", "enviada"};

        Cursor cursor = bd.query("mensagens", colunas, null, null, null, null,"categoria ASC");


        if(cursor.getCount()>0){
            cursor.moveToFirst();

            do{
                //Passar linha a linha e pegar os amigos
                /*Item i = new Item();
                i.setId(cursor.getInt(0));
                i.setNome(cursor.getString(1));
                i.setCategoria(cursor.getInt(2));
                i.setComprar(cursor.getInt(3) == 1);
                lista.add(i);*/

            }while(cursor.moveToNext());
        }

        return amigos;
    }


    public void fechar(){
        bd.close();
    }
}
