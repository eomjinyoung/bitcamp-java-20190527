<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>회원</h1>
<form action='update' method='post' enctype='multipart/form-data'>
<img src='/upload/member/${member.photo}' class='photo1'><br> 
<input type='file' name='file'><br>
번호: <input type='text' name='no' value='${member.no}' readonly><br>
이름: <input type='text' name='name' value='${member.name}'><br>
이메일: <input type='text' name='email' value='${member.email}'><br>
암호: <input type='text' name='password' value='${member.password}'><br>
전화: <input type='text' name='tel' value='${member.tel}'><br>
가입일: ${member.registeredDate}<br>
<button>변경</button>
<a href='delete?no=${member.no}'>삭제</a>
</form>
