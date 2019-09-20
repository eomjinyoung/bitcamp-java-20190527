<%@page import="com.eomcs.lms.domain.Member"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<a href='/member/add'>새 회원</a><br>
<table class='table table-hover'>
<tr>
  <th>번호</th>
  <th>이름</th>
  <th>이메일</th>
  <th>전화번호</th>
  <th>등록일</th>
</tr>
<%
List<Member> members = (List<Member>)request.getAttribute("members");
for (Member member : members) {
%>
  <tr>
    <td><%=member.getNo()%></td>
    <td><a href='/member/detail?no=<%=member.getNo()%>'><%=member.getName()%></a></td>
    <td><%=member.getEmail()%></td>
    <td><%=member.getTel()%></td>
    <td><%=member.getRegisteredDate()%></td>
  </tr>
<%}%>
</table>
<form action='/member/search'>
  검색어: <input type='text' name='keyword'>
  <button>검색</button>
</form>
</div>

<jsp:include page="../footer.jsp"/>

</body></html>
