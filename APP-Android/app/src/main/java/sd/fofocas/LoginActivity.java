package sd.fofocas;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Th on 03/05/2016.
 */
public class LoginActivity extends AppCompatActivity {

    EditText edtUsuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        this.edtUsuario = (EditText) findViewById(R.id.edtUsuario);

    }


    public void logar(View view) {
        String nome = edtUsuario.getText().toString().toUpperCase();
        nome = nome.trim();
        if(nome.isEmpty()){
            Toast.makeText(this,"Login Inválido",Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this,AmigosActivity.class);
            intent.putExtra("nome",nome);
            startActivity(intent);
            finish();
        }
    }
}
