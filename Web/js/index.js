var user = localStorage.getItem("user");

$(document).ready(function() {
	if (user != null) {
		$("#username").text("Bem vindo: " + user);
		carregar_mensagens();
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
  $("#conversas>div").removeClass("hidden").addClass("hidden");
  $("#conversas>#conversa-"+fila).removeClass("hidden");

});

$(document).on("click", "#addContato", function() {
	var novoContato = $("#nomeNovoContato").val();
	
	if(novoContato !== ""){
		novoContato = novoContato.toUpperCase();

		var contatosNoBanco = localStorage.getItem("contatos");
		var contatoCadastado = false;

		if(contatosNoBanco != null){
			contatosNoBanco = $.parseJSON(contatosNoBanco);
			$(contatosNoBanco).each(function(i, contatoAtual) {
				if (contatoAtual == novoContato) {
					contatoCadastado = true;
				}
			});
		}

		if (!contatoCadastado) {
			armazenar_contato(novoContato);
			$("#users a").removeClass("active");
			$("#users").append("<a href='#' class='list-group-item active' id='"+novoContato+"'>" + novoContato + "</a>");
			$("#conversas>div").removeClass("hidden").addClass("hidden");
			$("#conversas").append("<div id='conversa-"+novoContato+"' class='panel-body'></div>");
			/////////////////////
			//Trocar chat atual//
			/////////////////////
		}else{
			alert("Amigo ja existe!");
		}
		
			$("#nomeNovoContato").val("");
	}
});

$(document).on("click", "#send", function() {
	var htmlMsg = $("#mensagem").val();
	if(htmlMsg !== ""){
		var to = $("#users .active").attr("id");

		var tupla = {
			from: user,
			data: new Date(),
			msg: htmlMsg
		};

		send_message(JSON.stringify(tupla), to);
		armazenar_historico(to, tupla);
		$("#mensagem").val("");

		add_msg(to, tupla);
	}
});

function criar_amigos(){
		var contatosNoBanco = localStorage.getItem("contatos");
	
		if(contatosNoBanco != null){
			contatosNoBanco = $.parseJSON(contatosNoBanco);
			$(contatosNoBanco).each(function(i, amigo) {
				$("#users a").removeClass("active");
				$("#users").append("<a href='#' class='list-group-item active' id='"+amigo+"'>" + amigo + "</a>");
				$("#conversas>div").removeClass("hidden").addClass("hidden");
				$("#conversas").append("<div id='conversa-"+amigo+"' class='panel-body'></div>");
			});
		}
}

function listen_queue(queue) {
	setInterval(function() {
		$.ajax({
			method: "GET",
			url: "receiver.php",
			data: {
				'queue': queue
			},
		}).done(function(json) {
			if(json != " "){
				var response = $.parseJSON(json);

				var from = response['from'];
				var data = response['data'];
				var msg = response['msg'];

				//////////////////////////////
				//Modifica o HTML usando JS //
				//////////////////////////////
				if (response != "") {
					console.info("response:" + response['from']);
					armazenar_historico(from, response);
				}
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

function add_msg(fila, tupla){
		var msg = tupla["msg"];
		var data = tupla["data"];
		var pull = fila == tupla["from"] ? "recebi pull-left" : "enviei pull-right";
		$("#conversas>#conversa-"+fila).append("<div class='"+pull+"'>"+msg+"<br>"+data+"</div>");
}

function carregar_mensagens() {
	criar_amigos();
	var historico = localStorage.getItem("historico");
	historico = $.parseJSON(historico);
	$(historico).each(function(i, filas) {
		$.each(filas, function(filaAtual, tuplas) {
			$.each(tuplas, function(i, tupla){
				var quemEnviou = tupla['from'];
				var data = tupla['data'];
				var msg = tupla['msg'];
				
				add_msg(filaAtual, tupla);
				///////////////////
				//Colocar na tela//
				///////////////////
			
			});
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
	
	var historico = localStorage.getItem("historico");
	historico = $.parseJSON(historico);
	if (historico == null){
		historico = {};
		historico[from] = [];
	}
	if (historico[from] == null){
		historico[from] = [];
	}
	historico[from].push(tupla);
	localStorage.setItem("historico", JSON.stringify(historico));
}

function armazenar_contato(novoContato) {
	var contatos = localStorage.getItem("contatos");
	if (contatos == null){
		contatos = [];
	}else{
		contatos = $.parseJSON(contatos);
	}
	contatos.push(novoContato);
	localStorage.setItem("contatos", JSON.stringify(contatos));
}