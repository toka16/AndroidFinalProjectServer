/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    function categoryRowDesigner(url) {
        return function (row, t) {
            row.eq(1).css("cursor", "pointer").html('<img src="img/cross.png" width="20" height="20"> Delete').click(function () {
                var tr = $(this).parent();
                $.ajax({
                    url: url + row.eq(0).attr("title"),
                    type: "POST",
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


    function categoryFieldLengthChecker(row, data) {
        $('td', row).eq(0).attr('title', data[0]);
        if (data[0].length > 40) {
            $('td', row).eq(0).html(data[0].substring(0, 40) + '...');
        }
    }

    function categoryDataCollectior() {
        var data = {};
        data.name = $('#category_name').val();
        return data;
    }

    function categorySuccessor(table) {
        return function (data) {
            {
                return function () {
                    table.row.add([data.name, ""]).draw();
                    $('#category_name').val("");
                };
            }
            ;
        };
    }


    window.category_table = initTable('#categories', [
        {"width": "90%", "targets": 0},
        {"width": "10%", "targets": 1}
    ], tableRowCreated("category_table", categoryFieldLengthChecker, categoryRowDesigner("webapi/admin/category/")));


    fillTable('webapi/category', category_table, function (data) {
        return [data.name, ""];
    });

    submitListener('#new_category', 'webapi/admin/category', categoryDataCollectior, categorySuccessor(category_table));
});
