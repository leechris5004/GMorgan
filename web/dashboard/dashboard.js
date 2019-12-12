$(document).ready(function() {
    function updateTable() {
        $.get('http://localhost:4567/accounts', {email: User.email}).done(function(data) {
            var list = data.split('\n');
            for (var i = 0; i < list.length; i++) {
                var temp = list[i].split(',');
                $("#accounts").empty().append("<tr><td>"+temp[0]+"</td><td>"+temp[2]+"</td><td>"+temp[3]+"</td></tr>");
            }
        }).fail(function() {
            console.log("Get request has failed");
        });
    }
    User = {}
    var url = document.location.href,
        params = url.split('?')[1].split('&'), tmp;
    for (var i = 0, l = params.length; i < l; i++) {
         tmp = params[i].split('=');
         User[tmp[0]] = decodeURIComponent(tmp[1]);
    }
    updateTable();

    $(".container").on("click", "#deposit", function() {
        $(".container").empty().append('<label class="action"><b>Deposit</b></label>',
            '<input type="number" min="0" step="1" placeholder="Enter account ID" id="account" required>',
            '<input type="number" min="0.00" step="0.01" placeholder="Enter amount here ($)" id="amount" required>',
            '<div class="btn-group"><input type="button" id="acceptD" value="Enter"><input type="button" id="cancel" value="Cancel"></div>');
    });

    $(".container").on("click", "#withdraw", function() {
        $(".container").empty().append('<label class="action"><b>Withdraw</b></label>',
            '<input type="number" min="0" step="1" placeholder="Enter account ID" id="account" required>',
            '<input type="number" min="0.00" step="0.01" placeholder="Enter amount here ($)" id="amount" required>',
            '<div class="btn-group"><input type="button" id="acceptW" value="Enter"><input type="button" id="cancel" value="Cancel"></div>');
    });

    $(".container").on("click", "#acceptD", function() {
        var Amount = $("#amount").val();
        var Account = $("#account").val();
        $.post('http://localhost:4567/add', {account: Account, amount: Amount, positive: "true"}).done(function(data) {
            $('#error').text("Transaction Successful.");
            updateTable();
        }).fail(function() {
            console.log("Post request has failed");
        });
        $(".container").empty().append('<div class="btn-group"><input type="button" id="deposit" value="Deposit">' +
            '<input type="button" id="withdraw" value="Withdraw"></div>');
    });

    $(".container").on("click", "#acceptW", function() {
        var Amount = $("#amount").val();
        var Account = $("#account").val();
        $.post('http://localhost:4567/add', {account: Account, amount: Amount, positive: "false"}).done(function(data) {
            $('#error').text("Transaction Successful.");
            updateTable();
        }).fail(function() {
            console.log("Post request has failed");
        });
        $(".container").empty().append('<div class="btn-group"><input type="button" id="deposit" value="Deposit">' +
            '<input type="button" id="withdraw" value="Withdraw"></div>');
    });

    $(".container").on("click", "#cancel", function() {
        $(".container").empty().append('<div class="btn-group"><input type="button" id="deposit" value="Deposit">' +
            '<input type="button" id="withdraw" value="Withdraw"></div>');
    });
});