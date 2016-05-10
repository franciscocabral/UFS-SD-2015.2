<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html" lang="pt-br">
<head>
	<meta charset="utf-8"/>
	<title>Conversa atual</title>
	<link rel="stylesheet" href="css/bootstrap.css"/>
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
						<a href="#" class="list-group-item active" id="USER1">
							Contato 1
						</a>
						<a href="#" class="list-group-item" id="USER2">
							Contato 2
						</a>
						<a href="#" class="list-group-item" id="USER3">
							Contato 3
						</a>
					</div>
				</div>
				<div class="input-group">
						<input id="nomeNovoContato" type="text" class="form-control" placeholder="Digite novo contato" id="inContato"/>
							<span class="input-group-btn">
							<button id="addContato" class="btn btn-info" type="button">Adicionar</button>
							</span>
				</div>
			</div>
		</div>
		<div class="col-md-8">
			<div class="panel panel-info">
				<div class="panel-heading">
					Conversa atual
				</div>
				<div class="panel-body">
					<ul class="media-list">
						<li class="media">
							<div class="media-body">
								<div class="media">
									<div class="media-body">
										frase
										<br/>
										<small class="text-muted">USUÁRIO | HORA - DATA</small>
										<hr/>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="panel-footer">
					<div class="input-group">
						<input id="mensagem" type="text" class="form-control" placeholder="Digite sua mensagem" id="in1"/>
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
