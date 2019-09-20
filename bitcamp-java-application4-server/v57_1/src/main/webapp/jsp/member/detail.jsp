<%@page import="com.eomcs.lms.domain.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>회원 보기</title>
  <link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>
  <link rel='stylesheet' href='/css/common.css'>
</head>
<body>

<jsp:include page="../header.jsp"/>
    
<div id='content'>
<h1>회원</h1>
<%
Member member = (Member) request.getAttribute("member");
%>
<form action='/member/update' method='post' enctype='multipart/form-data'>
<img src='/upload/member/<%=member.getPhoto()%>' class='photo1'><br> 
<input type='file' name='photo'><br>
번호: <input type='text' name='no' value='<%=member.getNo()%>' readonly><br>
이름: <input type='text' name='name' value='<%=member.getName()%>'><br>
이메일: <input type='text' name='email' value='<%=member.getEmail()%>'><br>
암호: <input type='text' name='password' value='<%=member.getPassword()%>'><br>
전화: <input type='text' name='tel' value='<%=member.getTel()%>'><br>
가입일: <%=member.getRegisteredDate()%><br>
<button>변경</button>
<a href='/member/delete?no=<%=member.getNo()%>'>삭제</a>
</form>
</div>

<jsp:include page="../footer.jsp"/>

</body>
</html>
