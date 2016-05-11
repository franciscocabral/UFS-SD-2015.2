package sd.fofocas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.JsonObject;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by Th on 03/05/2016.
 */

public class AmigosActivity extends AppCompatActivity {

    private static ArrayList<Amigo> amigos = new ArrayList<>();
    AmigoAdapter adapter;
    ListView lvAmigos;
    String usuario;
    private BD bd;
    private boolean alreadyListened = false;
    private Connection connection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!getIntent().hasExtra("nome")) {
            Toast.makeText(AmigosActivity.this, "Login invalido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        usuario = getIntent().getStringExtra("nome");
        bd = new BD(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novo_chat();
            }
        });
        lvAmigos = (ListView) findViewById(R.id.lvAmigos);

        amigos = bd.buscarAmigos();

        adapter = new AmigoAdapter(this, amigos);
        lvAmigos.setAdapter(adapter);

        lvAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AmigosActivity.this, ChatActivity.class);
                intent.putExtra("nome", amigos.get(position).getNome());
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
        listen();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bd.fechar();
    }

    ConnectionFactory factory = new ConnectionFactory();

    public void novo_chat() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Nova Fofoca");
        final EditText edtNome = new EditText(this);
        edtNome.setHint("Digite o nome do seu contato");
        alert.setView(edtNome);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nome = edtNome.getText().toString().toUpperCase();
                Amigo amigo_novo = new Amigo(nome);
                amigos.add(amigo_novo);
                bd.inserir(new Mensagem("", (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(new Date()), true), amigo_novo);
                adapter.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton("Cancelar", null);
        alert.show();
    }

    public static Amigo getAmigoByName(String nome) {
        Amigo retorno = null;
        for (Amigo amigo : amigos) {
            if (amigo.getNome().contentEquals(nome)) {
                retorno = amigo;
                break;
            }
        }
        if (retorno == null) {
            retorno = new Amigo(nome);
        }
        return retorno;
    }

    class Msg {

        public String from;
        public String msg;
        public String data;

        public Msg() {
        }

        public Msg(String from, String msg, String data) {
            this.from = from;
            this.msg = msg;
            this.data = data;
        }

        public String toJson() {
            Gson g = new Gson();
            return g.toJson(this);
        }

        public void fromGson(JsonObject jsonObject) {
            jsonObject.get("from");
        }
    }

    private Connection getConnection() {
        try {
            if (connection == null) {
                factory = new ConnectionFactory();
                factory.setHost("franciscocabral.com");
                factory.setUsername("guest");
                factory.setPassword("guest");
                connection = factory.newConnection();
            }
        } catch (Exception e) {
            Log.e("TAG", "Erro ao conectar: " + e.getMessage(), e);
        }
        return connection;
    }

    public void listen() {
        if (!alreadyListened)
            new AsyncTask<Void, String, Void>() {
                @Override
                protected void onPreExecute() {
                    alreadyListened = true;
                }

                @Override
                protected void onProgressUpdate(String... values) {
                    try {
                        String json = values[0];
                        JSONObject jsonObject = new JSONObject(json);
                        String nome_amigo = jsonObject.getString("from");
                        Amigo amigo = AmigosActivity.getAmigoByName(nome_amigo);
                        if (amigo == null) {
                            amigos.add(new Amigo(nome_amigo));
                            adapter.notifyDataSetChanged();
                        }
                        Mensagem msg = new Mensagem(jsonObject.getString("msg"),jsonObject.getString("data"), false);
                        amigo.addMensagem(msg);
                        bd.inserir(msg,amigo);
                    } catch (Exception e) {
                        Log.e("TAG", "Erro na conversão de json: ", e);
                    }
                    super.onProgressUpdate(values);
                }

                @Override
                protected Void doInBackground(Void... params) {
                    Channel channel = null;
                    do {
                        try {
                            channel = getConnection().createChannel();
                            channel.queueDeclare(usuario, false, false, false, null);

                            Consumer consumer = new DefaultConsumer(channel) {
                                @Override
                                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                                        throws IOException {

                                    publishProgress(new String(body, "UTF-8"));
                                }
                            };
                            channel.basicConsume(usuario, true, consumer);
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            Log.e("TAG", "Erro ao receber: " + e.getMessage(), e);
                        }
                    } while (channel == null);
                    return null;
                }
            }.execute();
    }


}
