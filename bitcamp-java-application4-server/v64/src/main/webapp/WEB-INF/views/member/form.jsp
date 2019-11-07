<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>회원 등록</h1>
<form action='add' method='post' enctype='multipart/form-data'>
이름: <input type='text' name='name'><br>
이메일: <input type='text' name='email'><br>
암호: <input type='text' name='password'><br>
사진: <input type='file' name='file'><br>
전화: <input type='text' name='tel'><br>
<button>등록</button>
</form>
