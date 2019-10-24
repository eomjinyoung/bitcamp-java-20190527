<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>수업 등록</h1>
<form action='add' method='post'>
수업명: <input type='text' name='title'><br>
설명 : <textarea name='contents' rows='5' cols='50'></textarea><br>
시작일: <input type='text' name='startDate'><br>
종료일: <input type='text' name='endDate'><br>
총 수업시간: <input type='text' name='totalHours'><br>
일 수업시간: <input type='text' name='dayHours'><br>
<button>등록</button>
</form>
