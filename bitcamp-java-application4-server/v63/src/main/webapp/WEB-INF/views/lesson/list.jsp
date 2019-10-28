<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<h1>수업 목록</h1>
<a href='form'>새 수업</a><br>
<table class='table table-hover'>
<tr>
  <th>번호</th>
  <th>수업</th>
  <th>기간</th>
  <th>총강의시간</th>
</tr>
<c:forEach items="${lessons}" var="lesson">
  <tr>
    <td>${lesson.no}</td>
    <td><a href='detail?no=${lesson.no}'>${lesson.title}</a></td>
    <td>${lesson.startDate} ~ ${lesson.endDate}</td>
    <td>${lesson.totalHours}</td>
  </tr>
</c:forEach> 
</table>
