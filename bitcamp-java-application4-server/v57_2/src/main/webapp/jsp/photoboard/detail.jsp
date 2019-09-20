<%@page import="com.eomcs.lms.domain.PhotoFile"%>
<%@page import="java.util.List"%>
<%@page import="com.eomcs.lms.domain.PhotoBoard"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>사진게시물 보기</title>
  <link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>
  <link rel='stylesheet' href='/css/common.css'>
</head>
<body>

<jsp:include page="../header.jsp"/>
    
<div id='content'>
<h1>사진게시물</h1>
<form action='/photoboard/update'
      method='post' enctype='multipart/form-data'>
번호: <input type='text' name='no' value='${photoBoard.no}' readonly><br>
제목: <input type='text' name='title' value='${photoBoard.title}'><br>
수업: ${photoBoard.lessonNo}<br>
조회수: ${photoBoard.viewCount}<br>
<p>
<% 
PhotoBoard photoBoard = (PhotoBoard) request.getAttribute("photoBoard");
List<PhotoFile> files = photoBoard.getFiles();
for (PhotoFile file : files) {
  if (file.getFilePath() == null)
    continue;
  pageContext.setAttribute("file", file);
%>
  <img src='/upload/photoboard/${file.filePath}' class='photo2'> 
<%}%>
</p>
<% 
for (int i = 0; i < 6; i++) {
%>
  사진: <input type='file' name='filePath'><br>
<%}%>

<button>변경</button>
<a href='/photoboard/delete?no=${photoBoard.no}'>삭제</a>
</form>
</div>

<jsp:include page="../footer.jsp"/>

</body>
</html>
