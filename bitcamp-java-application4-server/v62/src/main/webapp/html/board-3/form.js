"use strict";

var fiNo = document.querySelector('#fiNo'),
    fiContents = document.querySelector('#fiContents'),
    staticCreatedDate = document.querySelector('#staticCreatedDate'),
    staticViewCount = document.querySelector('#staticViewCount'),
    btnAdd = document.querySelector('#btnAdd'),
    btnUpdate = document.querySelector('#btnUpdate'),
    btnDelete = document.querySelector('#btnDelete'),
    btnList = document.querySelector('#btnList');

var i = location.href.indexOf('?'); 
if (i != -1) {
  var str = location.href.substring(i + 1).split('=');
  if (str[0] == 'no') {
    if (!isNaN(parseInt(str[1])))
      var no = parseInt(str[1]);
    else 
      swal('게시물 조회!', '게시물 번호가 유효하지 않습니다!', 'error');
  }
}

btnList.onclick = () => {
  location.href = 'list.html';
};

if (no == undefined) {
  var el = document.querySelectorAll('.my-view-group')
  for (var e of el) {
    e.style['display'] = 'none';
  }
  
  btnAdd.onclick = () => {
    doAdd();
  };
  
} else {
  var el = document.querySelectorAll('.my-add-group')
  for (var e of el) {
    e.style['display'] = 'none';
  }
  doDetail();
  
  btnUpdate.onclick = () => {
    doUpdate();
  };
  
  btnDelete.onclick = () => {
    doDelete();
  };
  
}

function doAdd() {
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = () => {
    if (xhr.readyState < 4)
      return;
    
    if (xhr.status != 200) {
      alert("서버 요청 중 오류 발생!");
      return;
    }
    
    var data = JSON.parse(xhr.responseText);
    console.log(data.state);
    
    location.href = "list.html";
  };
  
  xhr.open("POST", "/app/json/board/add", true);
  xhr.setRequestHeader(
          "Content-Type", 
          "application/x-www-form-urlencoded");
  xhr.send(
      "contents=" + encodeURIComponent(fiContents.value));
}

function doDetail() {
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = () => {
    if (xhr.readyState < 4)
      return;
    
    if (xhr.status != 200) {
      alert("서버 요청 중 오류 발생!");
      return;
    }
    
    var data = JSON.parse(xhr.responseText);
    fiNo.value = data.result.no; 
    fiContents.value = data.result.contents;
    staticCreatedDate.value = data.result.createdDate;
    staticViewCount.value = data.result.viewCount;
    
  };
  xhr.open("GET", "/app/json/board/detail?no=" + no, true);
  xhr.send();
}

function doUpdate() {
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = () => {
    if (xhr.readyState < 4)
      return;
    
    if (xhr.status != 200) {
      alert("서버 요청 중 오류 발생!");
      return;
    }
    
    var data = JSON.parse(xhr.responseText);
    console.log(data.state);
    
    location.href = "list.html";
  };
  
  xhr.open("POST", "/app/json/board/update", true);
  xhr.setRequestHeader(
          "Content-Type", 
          "application/x-www-form-urlencoded");
  xhr.send(
      "no=" + fiNo.value +
      "&contents=" + encodeURIComponent(fiContents.value));
}

function doDelete() {
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = () => {
    if (xhr.readyState < 4)
      return;
    
    if (xhr.status != 200) {
      alert("서버 요청 중 오류 발생!");
      return;
    }
    
    var data = JSON.parse(xhr.responseText);
    console.log(data.state);
    
    location.href = "list.html";
  };
  xhr.open("GET", "/app/json/board/delete?no=" + no, true);
  xhr.send();
}