package sd.fofocas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;

import com.google.gson.Gson;

/**
 * Created by Th on 03/05/2016.
 */

public class AmigosActivity extends AppCompatActivity {

    private static ArrayList<Amigo> amigos = new ArrayList<>();
    AmigoAdapter adapter;
    ListView lvAmigos;
    String usuario;
    private BD bd;

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

        adapter = new AmigoAdapter(this,amigos);
        lvAmigos.setAdapter(adapter);

        final Handler incomingMessageHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String mensagem = msg.getData().getString("msg");
                String data = msg.getData().getString("data");
                String nome_amigo = msg.getData().getString("from");
                Amigo amigo = AmigosActivity.getAmigoByName(nome_amigo);
                if(amigo==null){
                    amigo = new Amigo(nome_amigo);
                    adapter.notifyDataSetChanged();
                }
                amigo.addMensagem(new Mensagem(mensagem,data,false));

            }
        };
        subscribe(incomingMessageHandler);

        lvAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AmigosActivity.this, ChatActivity.class);
                intent.putExtra("nome",amigos.get(position).getNome());
                startActivity(intent);
            }
        });
    }

    Thread subscribeThread;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscribeThread.interrupt();
        bd.fechar();
    }

    ConnectionFactory factory = new ConnectionFactory();
    private void setupConnectionFactory() {
        try {
            factory.setHost("franciscocabral.com");
            factory.setUsername("guest");
            factory.setPassword("guest");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    void subscribe(final Handler handler)
    {
        subscribeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Connection connection = factory.newConnection();
                        Channel channel = connection.createChannel();
                        channel.basicQos(1);
                        DeclareOk q = channel.queueDeclare();
                        channel.queueBind(q.getQueue(),usuario , usuario);
                        /*

                        Na linha de cima não sei qual dos dois substituo pela variavel usuario pra ler a fila

                         */
                        QueueingConsumer consumer = new QueueingConsumer(channel);
                        channel.basicConsume(q.getQueue(), true, consumer);
                        channel.queueDeclare(usuario, false, false, false, null);
                        channel.basicConsume(usuario, true, consumer);


                        // Process deliveries
                        while (true) {
                            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

                            String message = new String(delivery.getBody());
                            Log.d("","[r] " + message);

                            Message msg = handler.obtainMessage();

                            Bundle bundle = new Bundle();

                            bundle.putString("msg", message);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    } catch (InterruptedException e) {
                        break;
                    } catch (Exception e1) {
                        Log.d("", "Connection broken: " + e1.getClass().getName());
                        try {
                            Thread.sleep(4000); //sleep and then try again
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            }
        });
        subscribeThread.start();
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
                String nome = edtNome.getText().toString().toUpperCase();
                Amigo amigo_novo = new Amigo(nome);
                amigos.add(amigo_novo);
                bd.inserir(new Mensagem("",(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(new Date()),true),amigo_novo);
                adapter.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton("Cancelar",null);
        alert.show();
    }

    public static Amigo getAmigoByName(String nome){
        Amigo retorno=null;
        for(Amigo amigo:amigos){
            if(amigo.getNome().contentEquals(nome)){
                retorno = amigo;
                break;
            }
        }
        if (retorno==null){
            retorno = new Amigo(nome);
        }
        return retorno;
    }

    class Msg {

        public String from;
        public String msg;
        public String data;

        public Msg() {}

        public Msg(String from, String msg, String data) {
            this.from = from;
            this.msg = msg;
            this.data = data;
        }

        public String toJson(){
            Gson g = new Gson();
            return g.toJson(this);
        }

        public void fromGson(JsonObject jsonObject){
            jsonObject.get("from");
        }
    }



}
