"use strict";

//함수 이름이 길어서 짧은 이름의 변수를 만들어 함수 객체를 저장한다.
var $ = bitcamp;

var tbody = $("#list-table > tbody");

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
      $("<tr>")
        .html("<td>" + b.no + "</td>" + 
          "<td><a href='form.html?no=" + b.no + "'>" + b.contents + "</a></td>" + 
          "<td>" + b.createdDate + "</td>" +
          "<td>" + b.viewCount + "</td>")
        .appendTo(tbody);
    }
    
  };
  xhr.open("GET", "/app/json/board/list", true);
  xhr.send();
}

// 미니 jQuery 만들기
// 1) 태그를 쉽게 찾는 함수 만들기
// - 함수를 사용하기 쉽도록 기존의 DOM API를 포장한다.
function bitcamp(selector) {
  var e;
  
  if (selector.indexOf('<') == 0) {
    var tagName = selector.substring(1, selector.length - 1);
    e = document.createElement(tagName);
  } else {
    e = document.querySelector(selector);
  }
  
  // 기존 객체에 도우미 함수를 붙인다.
  e.append = function(child) {
    e.appendChild(child);
    return e;
  };
  
  e.appendTo = function(parent) {
    parent.appendChild(e);
    return e;
  }
  
  e.html = function(content) {
    e.innerHTML = content;
    return e;
  }
  
  
  return e;
}















