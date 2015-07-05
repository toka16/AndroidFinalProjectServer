/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function(){
    $("#form").submit(function(e){
        e.preventDefault();
        var user = {};
        user.username = $("#username").val();
        user.password = $("#password").val();
        $.ajax({
            headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
            },
            type: 'POST',
            url: 'webapi/users/login',
            data: JSON.stringify(user),
            success: function(data){
                window.location.replace('admin_panel.html');
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $('#status').html(jqXHR.responseText);
            }
        });
    });
});