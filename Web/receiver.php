<?php
	require('vendor/autoload.php');
	define('AMQP_DEBUG', FALSE);
	use PhpAmqpLib\Connection\AMQPConnection;
	use PhpAmqpLib\Message\AMQPMessage;


	$conn = new AMQPConnection("franciscocabral.com", 5672, "guest", "guest");
	$ch = $conn->channel();

	$exchange = 'amq.direct';
	$queue = $_GET['queue'];
	$ch->queue_declare($queue, FALSE, TRUE, FALSE, FALSE);
	$ch->exchange_declare($exchange, 'direct', TRUE, TRUE, FALSE);
	$ch->queue_bind($queue, $exchange);

	$msg_body = ' ';
	$msg = new AMQPMessage($msg_body, ['content_type' => 'text/plain', 'delivery_mode' => 2]);
	$ch->basic_publish($msg, $exchange);

	$retrived_msg = $ch->basic_get($queue);


	echo ($retrived_msg->body);


	$ch->basic_ack($retrived_msg->delivery_info['delivery_tag']);

	$ch->close();
	$conn->close();
	
?>
