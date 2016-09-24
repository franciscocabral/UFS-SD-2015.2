<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html" lang="pt-br">

<head>
	<meta charset="utf-8" />
	<title>Conversa atual</title>
	<link rel="stylesheet" href="css/bootstrap.css" />
	<link rel="stylesheet" href="css/site.css" />
	<script src="js/jquery-2.2.3.min.js"></script>
	<script src="js/index.js"></script>
</head>

<body style="font-family:Verdana">
	<div class="container">
		<div class="row " style="padding-top:40px;">
			<h3 id="username" class="text-center"></h3>

			<div class="col-md-4">
				<div class="panel panel-primary">
					<div class="panel-heading">
						Contatos
					</div>
					<div class="panel-body">
						<div class="list-group" id="users">
						</div>
					</div>
					<div class="input-group">
						<input id="nomeNovoContato" type="text" class="form-control" placeholder="Digite novo contato" id="inContato" />
						<span class="input-group-btn">
							<button id="addContato" class="btn btn-info" type="button">Adicionar</button>
							</span>
					</div>
				</div>
			</div>

			<div class="col-md-8">
				<div class="panel panel-info" >
					<div class="panel-heading">
						Conversa atual
					</div>
					<div id="conversas">
					</div>
					<div class="panel-footer">
						<div class="input-group">
							<input id="mensagem" type="text" class="form-control" placeholder="Digite sua mensagem" id="in1" />
							<span class="input-group-btn">
							<button id="send" class="btn btn-info" type="button">Enviar</button>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>