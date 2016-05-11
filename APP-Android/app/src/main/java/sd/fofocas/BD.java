package sd.fofocas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Th on 10/05/2016.
 */
public class BD {
    private SQLiteDatabase bd;
    BDCore bdCore;

    public BD(Context contexto){
        bdCore = new BDCore(contexto);
        bd = bdCore.getWritableDatabase();
    }

    public void inserir(Mensagem mensagem, Amigo amigo){
        bdCore.inserir(mensagem, amigo, bd);
    }

    public ArrayList<Amigo> buscarAmigos(){
        ArrayList<Amigo> amigos = new ArrayList<>();
        String[] colunas = new String[]{"amigo"};

        //Cursor cursor = bd.query("mensagens", colunas, null, null, null, null,"amigo ASC");
        Cursor cursor = bd.query(true,"mensagens",colunas,null,null,"amigo",null,"amigo ASC",null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();

            do{
                amigos.add(new Amigo(cursor.getString(0)));

            }while(cursor.moveToNext());
        }
        cursor.close();
        return amigos;
    }

    public ArrayList<Mensagem> buscarMensagem(Amigo amigo){
        ArrayList<Mensagem> mensagens = new ArrayList<>();
        String[] colunas = new String[]{"mensagem","data","enviada"};

        //Cursor cursor = bd.query("mensagens", colunas, null, null, null, null,"amigo ASC");
        Cursor cursor = bd.query("mensagens",colunas,"amigo = ?", new String[]{amigo.getNome()},null,null,"data ASC",null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();

            do{
               try{
                   mensagens.add(new Mensagem(cursor.getString(0),cursor.getString(1),cursor.getInt(2)==1?true:false));
               } catch(Exception e){
                   System.out.println(e);
               }
                Log.d("chat", ""+cursor.getCount());

            }while(cursor.moveToNext());
        }
        cursor.close();

        return mensagens;

    }

    public void fechar(){
        bd.close();
    }
}
