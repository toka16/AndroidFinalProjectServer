/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function(){
    function menuRowDesigner(url){
        return function(row, t){
            row.eq(3).html('<img src="' + row.eq(3).html() + '" width="50" height="50">');
            row.eq(4).css("cursor", "pointer").html('<img src="img/cross.png" width="20" height="20"> Delete').click(function(){
                var tr = $(this).parent();
                $.ajax({
                    url: url+row.eq(0).html(),
                    type: "POST",
                    success: function () {
                        window[t].row(tr).remove().draw();
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log("error: "+jqXHR);
                    }
                });

            });
        };
    }

    function menuFieldLengthChecker(row, data){
        for(var i=0; i<3; i++){
            if(data[i].length > 40){
                $('td', row).eq(i).html(data[1].substring(0, 40)+'...');
            }
            $('td', row).eq(i).attr('title', data[i]);
        }
    }    
    
    function menuDataCollectior(){
        var data = {};
        data.name = $('#menu_name').val();
        data.description = $('#menu_description').val();
        data.price = $('#menu_price').val();
        data.image_link = $('#menu_image').val();
        return data;
    }
    
    function menuSuccessor(table){
        return function(data){{
                return function(){
                    table.row.add([data.name, data.description, data.price, data.image_link, ""]).draw();
                    $('#menu_name').val("");
                    $('#menu_description').val("");
                    $('#menu_price').val("");
                    $('#menu_image').val("");
                };
            };
        };
    }
    

    window.menu_table = initTable('#menus', [
            {"width" : "20%", "targets" : 0},
            {"width" : "40%", "targets" : 1},
            {"width" : "15", "targets" : 2},
            {"width" : "20", "targets" : 3},
            {"width" : "5", "targets" : 4}
        ], tableRowCreated("menu_table", menuFieldLengthChecker, menuRowDesigner("webapi/admin/menu/")));


    fillTable('webapi/menu', menu_table, function(data){
        return [data.name, data.description, data.price, data.image_link ? data.image_link : "", ""];
    });
    
    submitListener('#new_menu', 'webapi/admin/menu', menuDataCollectior, menuSuccessor(menu_table));
});
