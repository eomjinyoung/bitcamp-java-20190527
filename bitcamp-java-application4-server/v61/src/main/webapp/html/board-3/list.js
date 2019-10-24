"use strict";

var tbody = document.querySelector("#list-table > tbody");

loadData();

function loadData() {
  //console.log("서버에서 데이터 가져오기!");
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = () => {
    if (xhr.readyState < 4)
      return;
    
    if (xhr.status != 200) {
      alert("서버 요청 중 오류 발생!");
      return;
    }
    
    var data = JSON.parse(xhr.responseText);
    //console.log(data);
    
    for (var b of data.result) {
      var tr = document.createElement("tr");
      tr.innerHTML = "<td>" + b.no + "</td>" + 
         "<td><a href='form.html?no=" + b.no + "'>" + b.contents + "</a></td>" + 
         "<td>" + b.createdDate + "</td>" +
         "<td>" + b.viewCount + "</td>";
      tbody.appendChild(tr);
    }
    
  };
  xhr.open("GET", "/app/json/board/list", true);
  xhr.send();
}
