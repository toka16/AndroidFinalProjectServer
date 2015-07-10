/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function initTable(id, defs, rowCreated){
    var table = $(id).DataTable({
        "columnDefs" : defs,
        "createdRow" : rowCreated
    });
   	
    return table;
}

function tableRowCreated(t, rowDesigner){
    return function(row){
        rowDesigner($('td', row), t);
    };
}


function fillTable(url, table, dataParser){
    $.ajax({
        headers: { 
            'Accept': 'application/json',
            'Content-Type': 'application/json' 
        },
        url: url,
        type: 'GET',
        success: function (data, textStatus, jqXHR) {
            for(var i=0; i<data.length; i++){
                table.row.add(dataParser(data[i])).draw();
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if(jqXHR.status === 401)
                    window.location = "index.html";
        }
    });
}

function submitListener(id, url, dataCollector, successor){
    $(id).submit(function(e){
        e.preventDefault();
        var data = dataCollector();
        $.ajax({
            headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
            },
            url: url,
            type: 'POST',
            data: JSON.stringify(data),
            success: successor,
            error: function (jqXHR, textStatus, errorThrown) {
                if(jqXHR.status === 401)
                    window.location = "index.html";
                else
                    alert(jqXHR.responseText);
            }
        });
    });
}

function selectAppend(id, value, label){
    $(id).append('<option value="' + value + '">' + label + '</option>');
}

function getImg(src){
    return '<img src="' + (src ? src : '') + '" width="50" height="50">';
}

function getDelete(){
    return '<img src="img/cross.png" width="20" height="20"> Delete';
}
