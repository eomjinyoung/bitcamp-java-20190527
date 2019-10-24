"use strict";

// 미니 jQuery 만들기
// 1) 태그를 쉽게 찾는 함수 만들기
// - 함수를 사용하기 쉽도록 기존의 DOM API를 포장한다.
function bitcamp(selector) {
  var arr = [];
  
  if (selector.indexOf('<') == 0) {
    var tagName = selector.substring(1, selector.length - 1);
    arr.push(document.createElement(tagName));
  } else {
    var el = document.querySelectorAll(selector);
    for (var e of el) {
      arr.push(e);
    }
  }
  
  // 기존 객체에 도우미 함수를 붙인다.
  arr.append = function(childs) {
    for (var e of arr) {
      for (var child of childs) {
        e.appendChild(child);
      }
    }
    return arr;
  };
  
  arr.appendTo = function(parents) {
    for (var parent of parents) {
      for (var e of arr) {
        parent.appendChild(e);
      }
    }
    return arr;
  };
  
  arr.html = function(content) {
    for (var e of arr) {
      e.innerHTML = content;
    }
    return arr;
  };
  
  arr.click = function(listener) {
    for (var e of arr) {
      e.addEventListener('click', listener);
    }
    return arr;
  };
  
  arr.css = function(propName, value) {
    for (var e of arr) {
      e.style[propName] = value;
    }
  };
  
  arr.val = function(value) {
    if (!value) {
      return arr[0].value;
      
    } else {
      for (var e of arr) {
        e.value = value;
      }
      return arr;
    }
  }
  
  return arr;
}

bitcamp.ajax = function(url, settings) {
  // settings 파라미터의 기본 값을 미리 설정한다.
  if (!settings.method) {
    settings.method = 'GET';
  } 
  
  if (!settings.data) {
    settings.data = {};
  }
  
  if (!settings.dataType) {
    settings.dataType = 'text';
  }
  
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = () => {
    if (xhr.readyState < 4)
      return;
    
    if (xhr.status != 200) {
      alert("서버 요청 중 오류 발생!");
      return;
    }
    
    if (settings.success) {
      if (settings.dataType == 'json') {
        var data = JSON.parse(xhr.responseText);
      } else {
        var data = xhr.responseText;
      }
      settings.success(data);
    }
  };
  
  if (settings.method == 'POST') {
    xhr.open("POST", url, true);
    xhr.setRequestHeader(
            "Content-Type", 
            "application/x-www-form-urlencoded");
    
    var qs = "";
    for (var propName in settings.data) {
      if (qs.length > 0) {
        qs += "&";
      }
      qs += propName + "=" + settings.data[propName];
    }
    
    xhr.send(qs);
    
  } else {
    xhr.open("GET", url, true);
    xhr.send();
  }
};

bitcamp.get = function(url, success, dataType) {
  if (!dataType) {
    dataType = 'json';
  }
  
  bitcamp.ajax(url, {
    'success': success,
    'dataType': dataType
  });
};

//함수 이름이 길어서 짧은 이름의 변수를 만들어 함수 객체를 저장한다.
var $ = bitcamp;









