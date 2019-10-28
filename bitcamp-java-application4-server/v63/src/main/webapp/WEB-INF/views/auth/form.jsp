<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>로그인 폼</h1>
<form action='login' method='post'>
이메일: <input type='text' name='email' 
             value='${cookie.email.value}'><br>
암호: <input type='text' name='password'><br>
<button>로그인</button>
</form>
