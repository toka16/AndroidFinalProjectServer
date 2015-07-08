/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    function productRowDesigner(url){
        return function(row, t){
            row.eq(4).css("display", "none").addClass("id");
            row.eq(3).css("cursor", "pointer").html('<img src="img/cross.png" width="20" height="20"> Delete').click(function(e){
                e.stopPropagation();
                var tr = $(this).parent();
                $.ajax({
                    url: url+row.eq(0).attr("title"),
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


    function productFieldLengthChecker(row, data){
        for(var i=0; i<2; i++){
            $('td', row).eq(i).attr('title', data[i]);
            if(data[i].length > 40){
                $('td', row).eq(i).html(data[i].substring(0, 40)+'...');
            }
        }
    }    
    
    function productDataCollector(){
        var data = {};
        data.name = $('#product_name').val();
        data.description = $('#product_description').val();
        data.price = $('#product_price').val();
        return data;
    }
    
    function productSuccessor(table){
        return function(data){
            table.row.add([data.name, data.description, data.price, "", data.id]).draw();
            $('#product_name').val("");
            $('#product_description').val("");
            $('#product_price').val("");
        };
    }
    

    window.product_table = initTable('#products', [
            {"width" : "20%", "targets" : 0},
            {"width" : "60%", "targets" : 1},
            {"width" : "15", "targets" : 2},
            {"width" : "5", "targets" : 3}
        ], tableRowCreated("product_table", productFieldLengthChecker, productRowDesigner("webapi/admin/product/")));


    fillTable('webapi/product', product_table, function(data){
        return [data.name, data.description, data.price, "", data.id];
    });
    
    submitListener('#new_product', 'webapi/admin/product', productDataCollector, productSuccessor(product_table));
    
    
    $('#products').on('click', 'tbody tr', function(e){
        alert("click");
    });
});