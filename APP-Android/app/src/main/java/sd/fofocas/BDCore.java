package sd.fofocas;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Th on 10/05/2016.
 */
public class BDCore extends SQLiteOpenHelper {

    private static final String NOME_BD = "Fofocas";
    private static final int VERSAO_BD = 3;


    public BDCore(Context context){
        super(context, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE mensagens(_id integer primary key autoincrement, amigo text not null, mensagem text not null, data text not null, enviada integer not null DEFAULT 0)");
        inserir(new Mensagem("",(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(new Date()),true),new Amigo("Thales"),bd);
        inserir(new Mensagem("",(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(new Date()),true),new Amigo("Rodrigo"),bd);
        inserir(new Mensagem("",(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(new Date()),true),new Amigo("Icaro"),bd);
        inserir(new Mensagem("",(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(new Date()),true),new Amigo("Francisco"),bd);

    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        bd.execSQL("drop table mensagens");
        onCreate(bd);
    }

    public void inserir(Mensagem mensagem, Amigo amigo, SQLiteDatabase bd){
        ContentValues valores = new ContentValues();

        valores.put("amigo", amigo.getNome());
        valores.put("mensagem", mensagem.getTexto());
        valores.put("data", mensagem.getData());
        valores.put("enviada", mensagem.isEnviada());

        bd.insert("mensagens",null,valores);
    }
}
