<?php
	require('vendor/autoload.php');
	define('AMQP_DEBUG', FALSE);
	use PhpAmqpLib\Connection\AMQPConnection;
	use PhpAmqpLib\Message\AMQPMessage;


	$conn = new AMQPConnection("franciscocabral.com", 5672, "guest", "guest");
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
