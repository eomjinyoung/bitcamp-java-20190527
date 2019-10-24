"use strict";

var tbody = $("#list-table > tbody");

loadData();
 
function loadData() {
  //console.log("서버에서 데이터 가져오기!");
  $.get("/app/json/board/list", function(data) {
    for (var b of data.result) {
      $("<tr>")
        .html("<td>" + b.no + "</td>" + 
          "<td><a href='form.html?no=" + b.no + "'>" + b.contents + "</a></td>" + 
          "<td>" + b.createdDate + "</td>" +
          "<td>" + b.viewCount + "</td>")
        .appendTo(tbody);
    }
  });
}













