<?php
	require('vendor/autoload.php');
	define('AMQP_DEBUG', FALSE);
	use PhpAmqpLib\Connection\AMQPConnection;
	use PhpAmqpLib\Message\AMQPMessage;


	//$conn = new AMQPConnection("franciscocabral.com", 5672, "guest", "guest");
	$url = parse_url('amqp://fthcmjci:TJWkglcMU8pbZjt89PYJRQV-Gi-SLD0g@black-boar.rmq.cloudamqp.com/fthcmjci');
	$conn = new AMQPConnection($url['host'], 5672, $url['user'], $url['pass'], substr($url['path'], 1));	
	$ch = $conn->channel();

	$queue = $_GET['queue'];
  $message = $_GET['msg'];

	$ch->queue_declare($queue, FALSE, TRUE, FALSE, FALSE);
	$msg = new AMQPMessage($message);
  $ch->basic_publish($msg, '', $queue);

	echo json_encode("sent to ".$queue);
	
	$ch->close();
	$conn->close();
	
?>
