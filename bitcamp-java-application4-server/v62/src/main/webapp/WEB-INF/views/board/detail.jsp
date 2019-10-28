<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>게시물</h1>
<form action='update' method='post'>
번호 : <input type='text' name='no' value='${board.no}' readonly><br>
내용 : <textarea name='contents' rows='5'
            cols='50'>${board.contents}</textarea><br>
등록일: ${board.createdDate}<br>
조회수: ${board.viewCount}<br>
<button>변경</button>
<a href='delete?no=${board.no}'>삭제</a>
</form>
