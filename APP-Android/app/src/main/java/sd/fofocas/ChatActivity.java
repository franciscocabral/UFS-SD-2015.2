package sd.fofocas;

/**
 * Created by Th on 28/04/2016.
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {

    private MensagemAdapter mensagemAdapter;
    private Amigo amigo;
    private BD bd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        ListView lv = (ListView) findViewById(R.id.lvChat);
        if (!getIntent().hasExtra("nome")){
            Toast.makeText(ChatActivity.this, "Amigo Inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        bd = new BD(this);
        String nome_amigo = getIntent().getStringExtra("nome");
        setTitle(nome_amigo);
        amigo = AmigosActivity.getAmigoByName(nome_amigo);
        amigo.setMensagens(bd.buscarMensagem(amigo));

        mensagemAdapter = new MensagemAdapter(this, amigo.getMensagens());
        lv.setAdapter(mensagemAdapter);

        setupConnectionFactory();
        publishToAMQP();
        setupPubButton();

    }

    void setupPubButton() {
        Button button = (Button) findViewById(R.id.publish);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText et = (EditText) findViewById(R.id.etText);
                String texto = et.getText().toString().trim();
                if(!texto.isEmpty()){
                    Mensagem msg = new Mensagem(texto,(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(new Date()),true);
                    amigo.addMensagem(msg);
                    bd.inserir(msg,amigo);
                    Gson g = new Gson();
                    Msg m = new Msg(amigo.getNome(),msg.getTexto(),msg.getData());
                    publishMessage(m.toJson());
                    et.setText("");
                }
            }
        });
    }

    Thread publishThread;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        publishThread.interrupt();
        bd.fechar();
    }

    private BlockingDeque<String> queue = new LinkedBlockingDeque<>();
    void publishMessage(String message) {
        //Adds a message to internal blocking queue
        try {
            Log.d("","[q] " + message);
            queue.putLast(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public void publishToAMQP()
    {
        publishThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Connection connection = factory.newConnection();
                        Channel ch = connection.createChannel();
                        ch.confirmSelect();

                        while (true) {
                            String message = queue.takeFirst();
                            try{
                                ch.basicPublish("", amigo.getNome(), null, message.getBytes());
                                Log.d("", "[s] " + message);
                                ch.waitForConfirmsOrDie();
                            } catch (Exception e){
                                Log.d("","[f] " + message);
                                queue.putFirst(message);
                                throw e;
                            }
                        }
                    } catch (InterruptedException e) {
                        break;
                    } catch (Exception e) {
                        Log.d("", "Connection broken: " + e.getClass().getName());
                        try {
                            Thread.sleep(5000); //sleep and then try again
                        } catch (InterruptedException e1) {
                            break;
                        }
                    }
                }
            }
        });
        publishThread.start();
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
    }
}