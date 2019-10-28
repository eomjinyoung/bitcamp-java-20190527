<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>사진게시물 목록</h1>
 
<a href='form'>새 사진게시물</a><br>

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
