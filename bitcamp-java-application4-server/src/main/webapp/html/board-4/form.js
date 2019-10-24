"use strict";

var fiNo = $('#fiNo'),
    fiContents = $('#fiContents'),
    staticCreatedDate = $('#staticCreatedDate'),
    staticViewCount = $('#staticViewCount'),
    btnAdd = $('#btnAdd'),
    btnUpdate = $('#btnUpdate'),
    btnDelete = $('#btnDelete'),
    btnList = $('#btnList');

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

btnList.click(() => {
  location.href = 'list.html';
});

if (no == undefined) {
  var el = $('.my-view-group')
  el.css('display', 'none');
  btnAdd.click(() => {
    doAdd();
  });
  
} else {
  var el = $('.my-add-group')
  el.css('display', 'none');
  
  doDetail();
  
  btnUpdate.click(() => {
    doUpdate();
  });
  
  btnDelete.click(() => {
    doDelete();
  });
}

function doAdd() {
  $.ajax("/app/json/board/add", {
    method: 'POST',
    data: {
      contents: encodeURIComponent(fiContents.val())
    },
    success: function(data) {
      console.log(data.state);
      location.href = "list.html";
    }
  });
}

function doDetail() {
  $.get("/app/json/board/detail?no=" + no, function(data) {
    fiNo.val(data.result.no); 
    fiContents.val(data.result.contents);
    staticCreatedDate.val(data.result.createdDate);
    staticViewCount.val(data.result.viewCount);
  });
}

function doUpdate() {
  $.ajax("/app/json/board/update", {
    method: 'POST',
    data: {
      no: no,
      contents: encodeURIComponent(fiContents.val())
    },
    success: function(data) {
      console.log(data.state);
      location.href = "list.html";
    }
  });
}

function doDelete() {
  $.get("/app/json/board/delete?no=" + no, function(data) {
    console.log(data.state);
    location.href = "list.html";
  });
}






