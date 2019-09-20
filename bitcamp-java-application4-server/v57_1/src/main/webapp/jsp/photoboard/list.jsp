<%@page import="com.eomcs.lms.domain.PhotoBoard"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<a href='/photoboard/add'>새 사진게시물</a><br>
<table class='table table-hover'>
<tr>
  <th>번호</th>
  <th>제목</th>
  <th>등록일</th>
  <th>조회수</th>
  <th>수업</th>
</tr>
<%
List<PhotoBoard> photoBoards = (List<PhotoBoard>) request.getAttribute("photoBoards");
for (PhotoBoard photoBoard : photoBoards) {
%>
  <tr>
    <td><%=photoBoard.getNo()%></td>
    <td><a href='/photoboard/detail?no=<%=photoBoard.getNo()%>'><%=photoBoard.getTitle()%></a></td>
    <td><%=photoBoard.getCreatedDate()%></td>
    <td><%=photoBoard.getViewCount()%></td>
    <td><%=photoBoard.getLessonNo()%></td>
  </tr>
<%}%>
</table>
</div>

<jsp:include page="../footer.jsp"/>

</body></html>
