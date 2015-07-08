/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function(){
    function menuRowDesigner(url){
        return function(row, t){
            row.eq(4).css("cursor", "pointer").click(function(e){
                e.stopPropagation();
                var tr = $(this).parent();
                $.ajax({
                    url: url+row.eq(5).html(),
                    type: "DELETE",
                    success: function () {
                        window[t].row(tr).remove().draw();
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log("error: "+jqXHR);
                    }
                });
            });
            row.eq(5).css("display", "none");
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
    
    function menuDataExtractor(data){
        function getImg(src){
            return '<img src="' + src + '" width="50" height="50">';
        }
        function getDelete(){
            return '<img src="img/cross.png" width="20" height="20"> Delete';
        }
        return [data.name, data.description, data.price, data.image_link ? getImg(data.image_link) : "", getDelete(), data.id];
    }

    window.menu_table = initTable('#menus', [
            {"width" : "20%", "targets" : 0},
            {"width" : "40%", "targets" : 1},
            {"width" : "15", "targets" : 2},
            {"width" : "20", "targets" : 3},
            {"width" : "5", "targets" : 4}
        ], tableRowCreated("menu_table", menuFieldLengthChecker, menuRowDesigner("webapi/admin/menu/")));


    fillTable('webapi/menu', menu_table, menuDataExtractor);
    
    submitListener('#new_menu', 'webapi/admin/menu', menuDataCollectior, menuSuccessor(menu_table));
    
    $('#menus').on('click', 'tbody tr', function(e){
        $('#edit_menu_id').val($("td", this).eq(5).html());
        $('#edit_menu_name').val($("td", this).eq(0).html());
        $('#edit_menu_description').val($("td", this).eq(1).html());
        $('#edit_menu_price').val($("td", this).eq(2).html());
        $('#edit_menu_image').val($("td", this).eq(3).find("img").attr("src")).change();
        $('#edit_menu').show();
        window.editing_menu_row = this;
    });
    
    $('#edit_menu_form').submit(function(e){
        e.preventDefault();
        var data = {};
        data.id = $('#edit_menu_id').val();
        data.name = $('#edit_menu_name').val();
        data.description = $('#edit_menu_description').val();
        data.price = $('#edit_menu_price').val();
        data.image_link = $('#edit_menu_image').val();
        $.ajax({
            headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
            },
            url: 'webapi/admin/menu',
            type: 'PUT',
            dataType: 'application/json',
            data: JSON.stringify(data),
            error: function (e){
                if(e.status === 200){
                    menu_table.row(editing_menu_row).data(menuDataExtractor(JSON.parse(e.responseText)));
                    $('#edit_menu').hide();
                }
            }
        });
    });
});
