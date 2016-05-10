package sd.fofocas;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Th on 10/05/2016.
 */
public class BDCore extends SQLiteOpenHelper {

    private static final String NOME_BD = "Fofocas";
    private static final int VERSAO_BD = 1;


    public BDCore(Context context){
        super(context, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE mensagens(_id integer primary key autoincrement, amigo text not null, mensagem text not null, data text not null, enviada integer not null DEFAULT 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        bd.execSQL("drop table mensagens");
        onCreate(bd);
    }

    private void inserir(Mensagem mensagem, String amigo, SQLiteDatabase bd){
        ContentValues valores = new ContentValues();

        valores.put("amigo", amigo);
        valores.put("mensagem", mensagem.getTexto());
        valores.put("data", mensagem.getData());
        valores.put("enviada", mensagem.isEnviada());

        bd.insert("mensagem",null,valores);
    }
}
