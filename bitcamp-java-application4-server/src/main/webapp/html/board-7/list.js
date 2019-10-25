"use strict";

var tbody = $("#list-table > tbody");
var trTemplateSrc = $('#t1').html();
var template = Handlebars.compile(trTemplateSrc);

loadData();
 
function loadData() { 
  //console.log("서버에서 데이터 가져오기!");
  $.get("/app/json/board/list", function(data) {
    for (var b of data.result) {
      $(template(b))
        .appendTo(tbody);
    }
  });
}













