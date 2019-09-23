<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>사진 게시물 목록</title>
  <link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>
  <link rel='stylesheet' href='/css/common.css'>
</head>
<body>

<jsp:include page="../header.jsp"/>

<div id='content'>
<h1>사진게시물 목록</h1> 
<a href='add'>새 사진게시물</a><br>
<table class='table table-hover'>
<tr>
  <th>번호</th>
  <th>제목</th>
  <th>등록일</th>
  <th>조회수</th>
  <th>수업</th>
</tr>
<c:forEach items="${photoBoards}" var="photoBoard">
  <tr>
    <td>${photoBoard.no}</td>
    <td><a href='detail?no=${photoBoard.no}'>${photoBoard.title}</a></td>
    <td>${photoBoard.createdDate}</td>
    <td>${photoBoard.viewCount}</td>
    <td>${photoBoard.lessonNo}</td>
  </tr>
</c:forEach> 
</table>
</div>

<jsp:include page="../footer.jsp"/>

</body></html>
