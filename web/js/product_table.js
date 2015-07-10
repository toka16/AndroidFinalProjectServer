/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    function productRowDesigner(url){
        return function(row, t){
            row.eq(5).css("display", "none");
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
        };
    } 
    
    function productDataCollector(){
        var data = {};
        data.name = $('#product_name').val();
        data.description = $('#product_description').val();
        data.price = $('#product_price').val();
        data.image_link = $('#product_image').val();
        return data;
    }
    
    function productSuccessor(table){
        return function(data){
            table.row.add([data.name, data.description, data.price, data.image_link, "", data.id]).draw();
            $('#product_name').val("");
            $('#product_description').val("");
            $('#product_price').val("");
            $('#product_image').val("");
        };
    }
    

    window.product_table = initTable('#products', [
            {"width" : "20%", "targets" : 0},
            {"width" : "40%", "targets" : 1},
            {"width" : "15", "targets" : 2},
            {"width" : "20", "targets" : 3},
            {"width" : "5", "targets" : 4}
        ], tableRowCreated("product_table", productRowDesigner("webapi/admin/product/")));

    function productDataExtractor(data){
        selectAppend('#add_menu_products', data.id, data.name);
        return [data.name, data.description, data.price, getImg(data.image_link), getDelete(), data.id];
    }

    fillTable('webapi/product', product_table, productDataExtractor);
    
    submitListener('#new_product', 'webapi/admin/product', productDataCollector, productSuccessor(product_table));
    
    
    $('#products').on('click', 'tbody tr', function(e){
        $('#edit_product_id').val($("td", this).eq(5).html());
        $('#edit_product_name').val($("td", this).eq(0).html());
        $('#edit_product_description').val($("td", this).eq(1).html());
        $('#edit_product_price').val($("td", this).eq(2).html());
        $('#edit_product_image').val($("td", this).eq(3).find("img").attr("src")).change();
        $('#edit_product').show();
        window.editing_product_row = this;
    });
    
    $('#edit_product_form').submit(function(e){
        e.preventDefault();
        var data = {};
        data.id = $('#edit_product_id').val();
        data.name = $('#edit_product_name').val();
        data.description = $('#edit_product_description').val();
        data.price = $('#edit_product_price').val();
        data.image_link = $('#edit_product_image').val();
        $.ajax({
            headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
            },
            url: 'webapi/admin/product',
            type: 'PUT',
            dataType: 'application/json',
            data: JSON.stringify(data),
            error: function (e){
                if(e.status === 200){
                    product_table.row(editing_product_row).data(productDataExtractor(JSON.parse(e.responseText))).draw();
                    $('#edit_product').hide();
                }
            }
        });
    });
});