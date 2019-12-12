$(document).ready(function() {
    $(".container").on("click", "#deposit", function() {
        $(".container").empty().append('<label class="action"><b>Deposit</b></label>',
            '<input type="number" min="0.00" step="0.01" placeholder="Enter amount here ($)" id="amount" required>',
            '<div class="btn-group"><input type="button" id="acceptD" value="Enter"><input type="button" id="cancel" value="Cancel"></div>');
    });

    $(".container").on("click", "#withdraw", function() {
        $(".container").empty().append('<label class="action"><b>Withdraw</b></label>',
            '<input type="number" min="0.00" step="0.01" placeholder="Enter amount here ($)" id="amount" required>',
            '<div class="btn-group"><input type="button" id="acceptW" value="Enter"><input type="button" id="cancel" value="Cancel"></div>');
    });

    $(".container").on("click", "#acceptD", function() {
        var Email = $("#email").val();
        var Amount = $("#amount").val();
        $.post('http://localhost:4567/add', {email: Email, amount: Amount, positive: "true"}).done(function(data) {
            if (data == 'true') {
                $('#error').text("Transaction Successful.");
            } else if (data == 'False') {
                $('#error').text("Transaction Failed. Try again.");
            }
        }).fail(function() {
            console.log("Post request has failed");
        });
        $(".container").empty().append('<div class="btn-group"><input type="button" id="deposit" value="Deposit">' +
            '<input type="button" id="withdraw" value="Withdraw"></div>');
    });

    $(".container").on("click", "#acceptW", function() {
        var Email = $("#email").val();
        var Amount = $("#amount").val();
        $.post('http://localhost:4567/remove', {email: Email, amount: Amount, positive: "false"}).done(function(data) {
            if (data == 'true') {
                $('#error').text("Transaction Successful.");
            } else if (data == 'False') {
                $('#error').text("Transaction Failed. Try again.");
            }
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