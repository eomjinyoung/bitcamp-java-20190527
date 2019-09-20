<%@page import="com.eomcs.lms.domain.Lesson"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>수업 목록</title>
  <link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>
  <link rel='stylesheet' href='/css/common.css'>
</head>
<body>

<jsp:include page="../header.jsp"/>

<div id='content'>
<h1>수업 목록</h1>
<a href='/lesson/add'>새 수업</a><br>
<table class='table table-hover'>
<tr>
  <th>번호</th>
  <th>수업</th>
  <th>기간</th>
  <th>총강의시간</th>
</tr>
<%
List<Lesson> lessons = (List<Lesson>)request.getAttribute("lessons");
for (Lesson lesson : lessons) {
  pageContext.setAttribute("lesson", lesson);
%>
  <tr>
    <td>${lesson.no}</td>
    <td><a href='/lesson/detail?no=${lesson.no}'>${lesson.title}</a></td>
    <td>${lesson.startDate} ~ ${lesson.endDate}</td>
    <td>${lesson.totalHours}</td>
  </tr>
<%}%>
</table>
</div>

<jsp:include page="../footer.jsp"/>

</body></html>
