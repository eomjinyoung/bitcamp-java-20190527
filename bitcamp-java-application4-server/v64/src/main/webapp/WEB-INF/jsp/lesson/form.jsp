<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>수업 등록</title>
  <link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>
  <link rel='stylesheet' href='/css/common.css'>
</head>
<body>

<jsp:include page="../header.jsp"/>
    
<div id='content'>
<h1>수업 등록</h1>
<form action='add' method='post'>
수업명: <input type='text' name='title'><br>
설명 : <textarea name='contents' rows='5' cols='50'></textarea><br>
시작일: <input type='text' name='startDate'><br>
종료일: <input type='text' name='endDate'><br>
총 수업시간: <input type='text' name='totalHours'><br>
일 수업시간: <input type='text' name='dayHours'><br>
<button>등록</button>
</form>
</div>

<jsp:include page="../footer.jsp"/>

</body>
</html>
