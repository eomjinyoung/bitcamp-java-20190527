<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<c:if test="${not empty refresh}">
  <meta http-equiv="Refresh" content="3;url=${refresh}">
</c:if>
  <title>실행 오류</title>
  <link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>
  <link rel='stylesheet' href='/css/common.css'>
</head>
<body>

<jsp:include page="header.jsp"/>
    
<div id='content'>
  <h1>실행 오류2!</h1>
  <p>${message}</p>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
<%! 
private static final Logger logger = LogManager.getLogger("error.jsp");
%>
<%    
Exception e = (Exception) request.getAttribute("error");
if (e != null) {
  // 왜 오류가 발생했는지 자세한 사항은 로그로 남긴다.
  StringWriter strOut = new StringWriter();
  e.printStackTrace(new PrintWriter(strOut));
  logger.error(strOut.toString());
}
%>