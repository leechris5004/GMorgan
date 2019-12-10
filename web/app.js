$(document).ready(function() {
    $("#login").click(function() {
        var email = $("#email").val();
        var password = $("#password").val();
        if ( email == '' || password =='') {
            $('input[type="text"],input[type="password"]').css("border","2px solid red");
            $('input[type="text"],input[type="password"]').css("box-shadow","0 0 3px red");
            $('#error').text("All fields must be filled");
        } else {
            console.log("Initialize post");
            $.post("http://localhost:4567/loginUser",{ email: email, password: password}, function(data) {
                if (data == 'Invalid Email') {
                    $('input[type="text"]').css({"border":"2px solid red","box-shadow":"0 0 3px red"});
                    $('input[type="password"]').css({"border":"2px solid #00F5FF","box-shadow":"0 0 5px #00F5FF"});
                } else if (data == 'Invalid Email or Password') {
                    $('input[type="text"],input[type="password"]').css({"border":"2px solid red","box-shadow":"0 0 3px red"});
                } else if (data == 'Login Successful') {
                    $("form")[0].reset();
                    $('input[type="text"],input[type="password"]').css({"border":"2px solid #00F5FF","box-shadow":"0 0 5px #00F5FF"});
                }
                $('#error').text(data);
            });
        }
    });
});