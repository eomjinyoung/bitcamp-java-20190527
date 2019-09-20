<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><head><title>로그인 폼</title>
  <link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>
  <link rel='stylesheet' href='/css/common.css'>
</head>
<body>

<jsp:include page="../header.jsp"/>
    
<div id='content'>
<h1>로그인 폼</h1>
<form action='/auth/login' method='post'>
이메일: <input type='text' name='email' 
             value='<%=request.getAttribute("email")%>'><br>
암호: <input type='text' name='password'><br>
<button>로그인</button>
</form>
</div>

<jsp:include page="../footer.jsp"/>

</body>
</html>
