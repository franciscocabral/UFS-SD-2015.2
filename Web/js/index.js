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

    var msg = {
        from: user,
        time: new Date(),
        msg: htmlMsg
    };

    send_message(JSON.stringify(msg), to);
});


function listen_queue(queue) {
    setInterval(function () {
        $.ajax({
            method: "GET",
            url: "receiver.php",
            data: {'queue': queue},
        }).done(function (json) {
            var response = $.parseJSON(json);
            //////////////////////////////
            //Modifica o HTML usando JS //
            //////////////////////////////
            console.log(response);
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