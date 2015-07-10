/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function(){
    $('#news_from_date').datepicker();
    $('#news_to_date').datepicker();
    $('#edit_news_from_date').datepicker();
    $('#edit_news_to_date').datepicker();
    
    
    function newsRowDesigner(url){
        return function(row, t){
            row.eq(5).hide();
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
        ], tableRowCreated("news_table", newsRowDesigner("webapi/admin/news/")));

    function newsDataExtractor(data){
        var img = '<img src="img/cross.png" width="20" height="20"> Delete';
        return [data.name, data.description, convertMillisecondsToDate(data.from_date), convertMillisecondsToDate(data.to_date), img, data.id];
    }

    fillTable('webapi/news', news_table, newsDataExtractor);
    
    submitListener('#new_news', 'webapi/admin/news', newsDataCollectior, newsSuccessor(news_table));
    
    
    $('#news').on('click', 'tbody tr', function(e){
        $('#edit_news_id').val($("td", this).eq(5).html());
        $('#edit_news_name').val($("td", this).eq(0).html());
        $('#edit_news_description').val($("td", this).eq(1).html());
        $('#edit_news_from_date').val($("td", this).eq(2).html());
        $('#edit_news_to_date').val($("td", this).eq(3).html());
        $('#edit_news').show();
        window.editing_news_row = this;
    });
    
    $('#edit_news_form').submit(function(e){
        e.preventDefault();
        var data = {};
        data.id = $('#edit_news_id').val();
        data.name = $('#edit_news_name').val();
        data.description = $('#edit_news_description').val();
        data.from_date = convertDateToMilliseconds($('#edit_news_from_date').val());
        data.to_date = convertDateToMilliseconds($('#edit_news_to_date').val());
        $.ajax({
            headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
            },
            url: 'webapi/admin/news',
            type: 'PUT',
            dataType: 'application/json',
            data: JSON.stringify(data),
            error: function (e){
                if(e.status === 200){
                    news_table.row(editing_news_row).data(newsDataExtractor(JSON.parse(e.responseText))).draw();
                    $('#edit_news').hide();
                }
            }
        });
    });
});
