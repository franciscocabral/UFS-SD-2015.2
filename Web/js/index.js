var user = localStorage.getItem("user");
var minhasMensagens = $.parseJSON(localStorage.getItem("mensagens"));

$(document).ready(function () {
    if (user != null) {
        $("#username").text("Bem vindo: " + user);
        listen_queue(user);
    } else {
        if (user = prompt("Quem é você?")) {
            user = user.toUpperCase();
            $("#username").text("Bem vindo: " + user);
            localStorage.setItem("user", user);
            listen_queue(user);
            carregar_do_banco();
        } else {
            alert("É necessário se identificar!");
            window.location = "/";
        }
    }
});

$(document).on("click", "#users a", function () {
    var fila = $(this).attr("id");
    $("#users a").removeClass("active");
    $(this).addClass("active");
    /////////////////////////////
    // Troca de conversa atual //
    /////////////////////////////
    console.log(fila);
});

$(document).on("click", "#send", function () {
    var htmlMsg = $("#mensagem").val();
    var to = $("#users .active").attr("id");

    var tupla = {
        from: user,
        time: new Date(),
        msg: htmlMsg
    };

    send_message(JSON.stringify(tupla), to);
    var historico = localStorage.getItem("historico");
    historico = $.parseJSON(historico);
    historico[to].push(tupla);
    localStorage.setItem("historico", JSON.stringify(tupla));
});


function listen_queue(queue) {
    setInterval(function () {
        $.ajax({
            method: "GET",
            url: "receiver.php",
            data: {'queue': queue},
        }).done(function (json) {
            var response = $.parseJSON(json);
            var from = response.from;
            var time = response.time;
            var msg = response.msg;
            
            //////////////////////////////
            //Modifica o HTML usando JS //
            //////////////////////////////
            console.log(response);
            
            var historico = localStorage.getItem("historico");
            historico = $.parseJSON(historico);
            //historico = { 
            //  "CHICO":[
            //     {from:"CHICO", msg:"bla", time:"..."}, 
            //     {from:"CHICO", msg:"bla", time:"..."},
            //     {from:"RODRIGO", msg:"bla", time:"..."}, 
            //     {from:"CHICO", msg:"bla", time:"..."}, 
            //     {from:"RODRIGO", msg:"bla", time:"..."}, 
            //  ], 
            //  "ICARO":[
            //     {from:"RODRIGO", msg:"bla", time:"..."}, 
            //     {from:"ICARO", msg:"bla", time:"..."}, 
            //     {from:"ICARO", msg:"bla", time:"..."}, 
            //     {from:"RODRIGO", msg:"bla", time:"..."}, 
            //     {from:"RODRIGO", msg:"bla", time:"..."}, 
            //  ] 
            //};
            historico[from].push(response);
            localStorage.setItem("historico", JSON.stringify(response));
        });
    }, 1000);
}

function send_message(msg, queue) {
    $.ajax({
        method: "GET",
        url: "sender.php",
        data: {'queue': queue, 'msg': msg},
    }).done(function (json) {
        var response = $.parseJSON(json);
        console.log(response);
    });
}

function carregar_do_banco(){
    //////////////////////////////////
    //Carrega histórico de mensagens//
    //////////////////////////////////
    var historico = localStorage.getItem(from);
    historico = $.parseJSON(historico);
    $(historico).each(function(i, filas){
        $(filas).each(function(filaAtual, tupla){
            var quemEnviou = tupla.from;
            var time = tupla.time;
            var msg = tupla.msg;
            ///////////////////
            //Colocar na tela//
            ///////////////////
        });
    });
}
