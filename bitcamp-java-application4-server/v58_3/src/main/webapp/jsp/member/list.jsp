<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>회원 목록</title>
  <link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>
  <link rel='stylesheet' href='/css/common.css'>
</head>
<body>

<jsp:include page="../header.jsp"/>

<div id='content'>
<h1>회원 목록</h1>
<a href='add'>새 회원</a><br>
<table class='table table-hover'>
<tr>
  <th>번호</th>
  <th>이름</th>
  <th>이메일</th>
  <th>전화번호</th>
  <th>등록일</th>
</tr>
<c:forEach items="${members}" var="member">
  <tr>
    <td>${member.no}</td>
    <td><a href='detail?no=${member.no}'>${member.name}</a></td>
    <td>${member.email}</td>
    <td>${member.tel}</td>
    <td>${member.registeredDate}</td>
  </tr>
</c:forEach> 
</table>
<form action='search'>
  검색어: <input type='text' name='keyword'>
  <button>검색</button>
</form>
</div>

<jsp:include page="../footer.jsp"/>

</body></html>
