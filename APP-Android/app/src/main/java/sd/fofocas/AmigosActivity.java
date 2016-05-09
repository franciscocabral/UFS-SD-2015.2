package sd.fofocas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Th on 03/05/2016.
 */

public class AmigosActivity extends AppCompatActivity {

    ArrayList<Amigo> amigos = new ArrayList<>();
    AmigoAdapter adapter;
    ListView lvAmigos;
    String usuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!getIntent().hasExtra("nome")){
            Toast.makeText(AmigosActivity.this, "Login invalido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        usuario = getIntent().getStringExtra("nome");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novo_chat();
            }
        });
        lvAmigos = (ListView) findViewById(R.id.lvAmigos);

        Amigo amigo1 = new Amigo("Thales",1);
        Amigo amigo2 = new Amigo("Rodrigo",2);
        Amigo amigo3 = new Amigo("Icaro",3);
        Amigo amigo4 = new Amigo("Francisco",4);

        amigos.add(amigo1);
        amigos.add(amigo2);
        amigos.add(amigo3);
        amigos.add(amigo4);

        adapter = new AmigoAdapter(this,amigos);
        lvAmigos.setAdapter(adapter);

        lvAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void novo_chat() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Nova Fofoca");
        final EditText edtNome = new EditText(this);
        edtNome.setHint("Digite o nome do seu contato");
        alert.setView(edtNome);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nome = edtNome.getText().toString();
                amigos.add(new Amigo(nome,amigos.size()+1));
                adapter.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton("Cancelar",null);
        alert.show();
    }
}
