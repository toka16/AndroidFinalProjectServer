/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function remove_menu_product(){
    var productID = $('#remove_menu_products').val();
    var productName = $('#remove_menu_products option:selected').text();
    $.ajax({
        url: 'webapi/menu/'+$('#edit_menu_id').val()+"/remove_product/"+productID,
        type: 'DELETE',
        success: function(){
            $('#remove_menu_products [value="' + productID + '"]').remove();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("Can't remove product, refresh page");
        }
    });
}

function add_menu_product(){
    var productID = $('#add_menu_products').val();
    var productName = $('#add_menu_products option:selected').text();
    $.ajax({
        url: 'webapi/menu/'+$('#edit_menu_id').val()+"/add_product/"+productID,
        type: 'POST',
        success: function(){
            selectAppend('#remove_menu_products', productID, productName);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("Can't add product, refresh page");
        }
    });
}


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
                table.row.add(menuDataExtractor(data)).draw();
                $('#menu_name').val("");
                $('#menu_description').val("");
                $('#menu_price').val("");
                $('#menu_image').val("");
            };
        };
    }
    
    function menuDataExtractor(data){
        return [data.name, data.description, data.price, getImg(data.image_link), getDelete(), data.id];
    }

    window.menu_table = initTable('#menus', [
            {"width" : "20%", "targets" : 0},
            {"width" : "40%", "targets" : 1},
            {"width" : "15", "targets" : 2},
            {"width" : "20", "targets" : 3},
            {"width" : "5", "targets" : 4}
        ], tableRowCreated("menu_table",  menuRowDesigner("webapi/admin/menu/")));


    fillTable('webapi/menu', menu_table, menuDataExtractor);
    
    submitListener('#new_menu', 'webapi/admin/menu', menuDataCollectior, menuSuccessor(menu_table));
    
    $('#menus').on('click', 'tbody tr', function(e){
        if(window.editing_menu_row === this)
            return;
        $.ajax({
            url: 'webapi/menu/'+$("td", this).eq(5).html()+"/containing_products",
            type: "GET",
            success: function(data){
                $('#remove_menu_products').empty();
                for(var i=0; i<data.length; i++){
                    selectAppend('#remove_menu_products', data[i].id, data[i].name);
                }

            },
            error: function (jqXHR, textStatus, errorThrown) {
                selectAppend('#remove_menu_products', 'default', "No Products");
            }
        });
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
