/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    $('#new_product').submit(function(e){
        e.preventDefault();
        var data = {};
        data.name = $('#name').val();
        data.description = $('#description').val();
        data.price = $('#price').val();
        $.ajax({
            headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
            },
            url: 'webapi/admin/products',
            type: 'POST',
            data: JSON.stringify(data),
            success: function(){
                table.row.add([data.name, data.description, data.price]).draw();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                if(jqXHR.status === 401)
                    window.location.replace("index.html");
            }
        });
    });
    
    window.table = $('#products').DataTable({
        "columnDefs" : [
            {"width" : "20%", "targets" : 0},
            {"width" : "60%", "targets" : 1}
        ],
        "createdRow": function ( row, data, index ) {
            if(data[1].length > 100){
                $('td', row).eq(1).html(data[1].substring(0, 100)+'...');
                $('td', row).eq(1).attr('title', data[1]);
            }
            
        }
    });
    $('#products').attr("max-height", $(window).height()/2);
    $.ajax({
        headers: { 
            'Accept': 'application/json',
            'Content-Type': 'application/json' 
        },
        url: 'webapi/products',
        type: 'GET',
        success: function (data, textStatus, jqXHR) {
            for(var i=0; i<data.length; i++){
                table.row.add([data[i].name, data[i].description, data[i].price]).draw();
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if(jqXHR.status === 401)
                    window.location.replace("index.html");
        }
    });
});       