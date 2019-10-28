<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>사진게시물</h1>
<form action='update'
      method='post' enctype='multipart/form-data'>
번호: <input type='text' name='no' value='${photoBoard.no}' readonly><br>
제목: <input type='text' name='title' value='${photoBoard.title}'><br>
수업: ${photoBoard.lessonNo}<br>
조회수: ${photoBoard.viewCount}<br>
<p>
<c:forEach items="${photoBoard.files}" var="file">
  <img src='/upload/photoboard/${file.filePath}' class='photo2'> 
</c:forEach>
</p>
<c:forEach begin="1" end="6">
  사진: <input type='file' name='filePath'><br>
</c:forEach>

<button>변경</button>
<a href='delete?no=${photoBoard.no}'>삭제</a>
</form>
