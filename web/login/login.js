$(document).ready(function() {
    $("#login").click(function() {
        var Email = $("#email").val();
        var Password = $("#password").val();
        if ( Email == '' || Password =='    ') {
            $('input[type="text"],input[type="password"]').css("border","2px solid red");
            $('input[type="text"],input[type="password"]').css("box-shadow","0 0 3px red");
            $('#error').text("All fields must be filled");
        } else {
            $.post('http://localhost:4567/login', {email: Email, password: Password}).done(function(data) {
                if (data == "true") {
                    $("form")[0].reset();
                    location.href = '../dashboard/dashboard.html';
                } else if (data == "false") {
                    $('input[type="text"],input[type="password"]').css({"border":"2px solid red","box-shadow":"0 0 3px red"});
                    $('#error').text("Invalid Username or Password");
                }
            }).fail(function() {
                console.log("Post request has failed");
            });
        }
    });
});