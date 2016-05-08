#!/usr/bin/env node

var amqp = require('amqplib/callback_api');

amqp.connect('amqp://ptkkvumz:h5CbPFfnbuXdVhsazHhLB8QJBSetOEls@jellyfish.rmq.cloudamqp.com/ptkkvumz', function(err, conn) {
  conn.createChannel(function(err, ch) {
    var destinatario = 'Pessoa1';

    ch.assertQueue(destinatario, {durable: false});
    ch.sendToQueue(destinatario, new Buffer('Este é um exemplo!'));
    console.log(" [x] Mensagem Enviada");
  });
  setTimeout(function() { conn.close(); process.exit(0) }, 500);
});
