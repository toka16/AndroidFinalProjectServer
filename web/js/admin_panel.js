/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function logout(){
    $.ajax({
        url: 'webapi/admin/logout',
        type: 'POST',
        success: function(){
            window.location = 'index.html';
        }
    });
}

$(document).ready(function(){
    var hash = window.location.hash;
    if(hash === ""){
        $('.tabs .tab-links').children().first().addClass('active');
        $('.tab').first().addClass('active');
    }else{
        $(hash).addClass('active');
        $('.tabs .tab-links').find('[href="' + hash + '"]').parent().addClass("active");
    }
    
    jQuery('.tabs .tab-links a').on('click', function(e)  {
        var currentAttrValue = jQuery(this).attr('href');
 
        jQuery('.tabs ' + currentAttrValue).show().siblings().hide();
 
        jQuery(this).parent('li').addClass('active').siblings().removeClass('active');
        
        window.location = window.location.toString().split('#')[0] + currentAttrValue;
 
        e.preventDefault();
    });
    
    $('#new_user_form').submit(function (e){
        e.preventDefault();
        var data = $(this).serialize();
        $.ajax({
            url: 'webapi/admin/register',
            type: 'POST',
            data: data,
            success: function(data){
                $('#new_user_form').find('input').val('').last().val('Add User');
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("Couldn't add new User");
                console.log('error: '+jqXHR);
            }
        });
    });
});