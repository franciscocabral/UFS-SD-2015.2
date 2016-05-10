var user = localStorage.getItem("user");

$(document).ready(function() {
	if (user != null) {
		$("#username").text("Bem vindo: " + user);
		carregar_do_banco();
		listen_queue(user);
	} else {
		if (user = prompt("Quem é você?")) {
			user = user.toUpperCase();
			$("#username").text("Bem vindo: " + user);
			localStorage.setItem("user", user);
			listen_queue(user);
		} else {
			alert("É necessário se identificar!");
			window.location = "/";
		}
	}
});

$(document).on("click", "#users a", function() {
	var fila = $(this).attr("id");
	$("#users a").removeClass("active");
	$(this).addClass("active");
	/////////////////////////////
	// Troca de conversa atual //
	/////////////////////////////
	//media-body
	//$("#users").append("<li class='list-group-item'>"+novoContato+"</li>");

	console.log(fila);
});

$(document).on("click", "#addContato", function() {
	var novoContato = $("#nomeNovoContato").val();
	novoContato = novoContato.toUpperCase();

	var contatosNoBanco = localStorage.getItem("contatos");
	var contatoCadastado = false;
	$(contatosNoBanco).each(function(i, contatoAtual) {
		if (contatoAtual == novoContato) {
			contatoCadastado = true;
		}
	});

	if (!contatoCadastado) {
		$("#users").append("<li class='list-group-item'>" + novoContato + "</li>");
		armazenar_contato(novoContato); //Nao esta cadastrando!!!!!!!!
	}
});

$(document).on("click", "#send", function() {
	var htmlMsg = $("#mensagem").val();
	var to = $("#users .active").attr("id");

	var tupla = {
		from: user,
		data: new Date(),
		msg: htmlMsg
	};

	send_message(JSON.stringify(tupla), to);

	armazenar_historico(to, tupla);

	//     var historico = localStorage.getItem("historico");
	//     historico = $.parseJSON(historico);
	//     historico[to].push(tupla);
	//     localStorage.setItem("historico", JSON.stringify(tupla));
});


function listen_queue(queue) {
	setInterval(function() {
		$.ajax({
			method: "GET",
			url: "receiver.php",
			data: {
				'queue': queue
			},
		}).done(function(json) {
			var response = $.parseJSON(json);
			console.log(response);
			var from = response['from'];
			var data = response['data'];
			var msg = response['msg'];

			//////////////////////////////
			//Modifica o HTML usando JS //
			//////////////////////////////
			if (response != "") {
				console.info("response:" + response);
				armazenar_historico(from, response);
			}
		});
	}, 1000);
}

function send_message(msg, queue) {
	$.ajax({
		method: "GET",
		url: "sender.php",
		data: {
			'queue': queue,
			'msg': msg
		},
	}).done(function(json) {
		var response = $.parseJSON(json); //Resposta?
		console.log(response);
	});
}

function carregar_do_banco() {
	//////////////////////////////////
	//Carrega histórico de mensagens//
	//////////////////////////////////
	var historico = localStorage.getItem("historico");
	historico = $.parseJSON(historico);
	$(historico).each(function(i, filas) {
		$(filas).each(function(filaAtual, tupla) {
			var quemEnviou = tupla.from;
			var data = tupla.data;
			var msg = tupla.msg;
			///////////////////
			//Colocar na tela//
			///////////////////
		});
	});
}

function carregar_contatos_do_banco() {
	var contatos = localStorage.getItem("contatos");
	contatos = $.parseJSON(contatos);

	$(contatos).each(function(i, contatoAtual) {
		$("#users").append("<li class='list-group-item'>" + contatoAtual + "</li>");
	});
}

function armazenar_historico(from, tupla) {
	var historico = localStorage.getItem("historico");
	historico = $.parseJSON(historico);
	if (typeof(historico == "undefined"))
		historico = {};
	//historico = { 
	//  "CHICO":[
	//     {from:"CHICO", msg:"bla", time:"..."}, 
	//     {from:"CHICO", msg:"bla", time:"..."},
	//     {from:"RODRIGO", msg:"bla", time:"..."}, 
	//     {from:"CHICO", msg:"bla", time:"..."}, 
	//     {from:"RODRIGO", msg:"bla", time:"..."}, 
	// ], 
	//  "ICARO":[
	//     {from:"RODRIGO", msg:"bla", time:"..."}, 
	//     {from:"ICARO", msg:"bla", time:"..."}, 
	//     {from:"ICARO", msg:"bla", time:"..."}, 
	//     {from:"RODRIGO", msg:"bla", time:"..."}, 
	//     {from:"RODRIGO", msg:"bla", time:"..."}, 
	//  ] 
	//};
	if (typeof(historico[from]) == "undefined")
		historico[from] = [];
	historico[from].push(tupla);
	localStorage.setItem("historico", JSON.stringify(historico));
}

function armazenar_contato(novoContato) {
	var contatos = localStorage.getItem("contatos");
	contatos = $.parseJSON(contatos);
	contatos.push(novoContato);
	localStorage.setItem("contatos", JSON.stringify(contatos));
}