package sd.fofocas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;


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

        final Handler incomingMessageHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String message = msg.getData().getString("msg");
                Date now = new Date();
                //amigo.addMensagem(message, now, false);
                //descobrir amigo na mensagem pra mandar pro amigo correto
            }
        };
        subscribe(incomingMessageHandler);

        lvAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Iniciar o Chat Activity
            }
        });
    }

    Thread subscribeThread;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscribeThread.interrupt();
    }

    ConnectionFactory factory = new ConnectionFactory();
    private void setupConnectionFactory() {
        String uri = "amqp://fthcmjci:TJWkglcMU8pbZjt89PYJRQV-Gi-SLD0g@black-boar.rmq.cloudamqp.com/fthcmjci";
        try {
            factory.setAutomaticRecoveryEnabled(false);
            factory.setUri(uri);
        } catch (KeyManagementException | NoSuchAlgorithmException | URISyntaxException e1) {
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
                        channel.queueBind(q.getQueue(), "amq.fanout", "chat");
                        QueueingConsumer consumer = new QueueingConsumer(channel);
                        channel.basicConsume(q.getQueue(), true, consumer);

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
                amigos.add(new Amigo(nome,amigos.size()+1));
                adapter.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton("Cancelar",null);
        alert.show();
    }
}
