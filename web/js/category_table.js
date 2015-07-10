/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    function categoryRowDesigner(url) {
        return function (row, t) {
            row.eq(2).hide();
            row.eq(1).css("cursor", "pointer").click(function (e) {
                e.stopPropagation();
                var tr = $(this).parent();
                $.ajax({
                    url: url + row.eq(2).html(),
                    type: "DELETE",
                    success: function () {
                        window[t].row(tr).remove().draw();
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log("error: " + jqXHR);
                    }
                });

            });
        };
    }

    function categoryDataCollectior() {
        var data = {};
        data.name = $('#category_name').val();
        return data;
    }

    function categorySuccessor(table) {
        return function (data) {
            table.row.add(categoryDataExtractor(data)).draw();
            $('#category_name').val("");
        };
    }

    function categoryDataExtractor(data){
        return [data.name, getDelete(), data.id];
    }

    window.category_table = initTable('#categories', [
        {"width": "90%", "targets": 0},
        {"width": "10%", "targets": 1}
    ], tableRowCreated("category_table", categoryRowDesigner("webapi/admin/category/")));


    fillTable('webapi/category', category_table, categoryDataExtractor);

    submitListener('#new_category', 'webapi/admin/category', categoryDataCollectior, categorySuccessor(category_table));
    
    $('#categories').on('click', 'tbody tr', function(e){
        $('#edit_category_id').val($("td", this).eq(2).html());
        $('#edit_category_name').val($("td", this).eq(0).html());
        $('#edit_category').show();
        window.editing_category_row = this;
    });
    
    $('#edit_category_form').submit(function(e){
        e.preventDefault();
        var data = {};
        data.id = $('#edit_category_id').val();
        data.name = $('#edit_category_name').val();
        $.ajax({
            headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
            },
            url: 'webapi/admin/category',
            type: 'PUT',
            dataType: 'application/json',
            data: JSON.stringify(data),
            error: function (e){
                if(e.status === 200){
                    category_table.row(editing_category_row).data(categoryDataExtractor(JSON.parse(e.responseText))).draw();
                    $('#edit_category').hide();
                }
            }
        });
    });
});
