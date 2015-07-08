/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function(){
    $('#news_from_date').datepicker();
    $('#news_to_date').datepicker();
    
    
    function newsRowDesigner(url){
        return function(row, t){
            row.eq(4).css("cursor", "pointer").html('<img src="img/cross.png" width="20" height="20"> Delete').click(function(){
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


    function newsFieldLengthChecker(row, data){
        for(var i=0; i<2; i++){
            $('td', row).eq(i).attr('title', data[i]);
            if(data[i].length > 40){
                $('td', row).eq(i).html(data[i].substring(0, 40)+'...');
            }
        }
    }
   
    
    function newsDataCollectior(){
        var data = {};
        data.name = $('#news_name').val();
        data.description = $('#news_description').val();
        data.from_date = convertDateToMilliseconds($('#news_from_date').val());
        data.to_date = convertDateToMilliseconds($('#news_to_date').val());
        console.log(data);
        return data;
    }
    
    function newsSuccessor(table){
        return function(data){{
                return function(){
                    table.row.add([data.name, data.description, data.from_date, data.to_date, ""]).draw();
                    $('#news_name').val("");
                    $('#news_description').val("");
                    $('#news_from_date').val("");
                    $('#news_to_date').val("");
                };
            };
        };
    }
    
    function convertDateToMilliseconds(date){
        var arr = date.split("/");
        return new Date(arr[2], arr[0]-1, arr[1]).getTime();
    }
    
    function convertMillisecondsToDate(time){
        var date = new Date(time);
        return date.toDateString();
    }

    window.news_table = initTable('#news', [
            {"width" : "20%", "targets" : 0},
            {"width" : "55%", "targets" : 1},
            {"width" : "10", "targets" : 2},
            {"width" : "10", "targets" : 3},
            {"width" : "5", "targets" : 4}
        ], tableRowCreated("news_table", newsFieldLengthChecker, newsRowDesigner("webapi/admin/news/")));

    

    fillTable('webapi/news', news_table, function(data){
        return [data.name, data.description, convertMillisecondsToDate(data.from_date), convertMillisecondsToDate(data.to_date), ""];
    });
    
    submitListener('#new_news', 'webapi/admin/news', newsDataCollectior, newsSuccessor(news_table));
});
