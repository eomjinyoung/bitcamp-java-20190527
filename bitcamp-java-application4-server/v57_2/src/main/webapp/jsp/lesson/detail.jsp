<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>수업 보기</title>
  <link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>
  <link rel='stylesheet' href='/css/common.css'>
</head>
<body>

<jsp:include page="../header.jsp"/>
    
<div id='content'>
<h1>수업</h1>
<form action='/lesson/update' method='post'>
번호: <input type='text' name='no' value='${lesson.no}' readonly><br>
수업명: <input type='text' name='title' value='${lesson.title}'><br>
설명: <textarea name='contents' rows='5' cols='50'>${lesson.contents}</textarea><br>
시작일: <input type='text' name='startDate' value='${lesson.startDate}'><br>
종료일: <input type='text' name='endDate' value='${lesson.endDate}'><br>
총 수업시간: <input type='text' name='totalHours' value='${lesson.totalHours}'><br>
일 수업시간: <input type='text' name='dayHours' value='${lesson.dayHours}'><br>
<button>변경</button>
<a href='/lesson/delete?no=${lesson.no}'>삭제</a>
</form>
</div>

<jsp:include page="../footer.jsp"/>

</body>
</html>
