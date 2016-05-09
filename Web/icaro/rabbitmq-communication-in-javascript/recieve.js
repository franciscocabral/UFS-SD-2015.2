#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

amqp.connect('amqp://ptkkvumz:h5CbPFfnbuXdVhsazHhLB8QJBSetOEls@jellyfish.rmq.cloudamqp.com/ptkkvumz', function(err, conn) {
  conn.createChannel(function(err, ch) {
    var remetente = 'Pessoa1';

    ch.assertQueue(remetente, {durable: false});
    console.log(" [*] Aguardando mensagens de %s. Para sair aperte CTRL+C", remetente);
    ch.consume(remetente, function(msg) {
      console.log(" [x] Recebido %s", msg.content.toString());
    }, {noAck: true});
  });
});
