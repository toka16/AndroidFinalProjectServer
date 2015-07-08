/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function(){
    $("#form").submit(function(e){
        e.preventDefault();
        $.ajax({
            type: 'POST',
            url: 'webapi/admin/login',
            data: $(this).serialize(),
            success: function(){
                window.location  = "admin_panel.html";
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $('#status').html(jqXHR.responseText);
            }
        });
    });
});