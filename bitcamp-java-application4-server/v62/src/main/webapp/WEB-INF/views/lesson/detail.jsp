<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>수업</h1>
<form action='update' method='post'>
번호: <input type='text' name='no' value='${lesson.no}' readonly><br>
수업명: <input type='text' name='title' value='${lesson.title}'><br>
설명: <textarea name='contents' rows='5' cols='50'>${lesson.contents}</textarea><br>
시작일: <input type='text' name='startDate' value='${lesson.startDate}'><br>
종료일: <input type='text' name='endDate' value='${lesson.endDate}'><br>
총 수업시간: <input type='text' name='totalHours' value='${lesson.totalHours}'><br>
일 수업시간: <input type='text' name='dayHours' value='${lesson.dayHours}'><br>
<button>변경</button>
<a href='delete?no=${lesson.no}'>삭제</a>
</form>
