<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>게시물 목록</h1>
<a href='form'>새 글</a>
<select id="pageSize">
  <option value="3">3</option>
  <option value="8">8</option>
  <option value="10">10</option>
  <option value="20">20</option>
</select>
<table class='table table-hover'>
<tr>
  <th>번호</th>
  <th>내용</th>
  <th>등록일</th>
  <th>조회수</th>
</tr>
<c:forEach items="${boards}" var="board">
  <tr>
    <td>${board.no}</td>
    <td><a href='detail?no=${board.no}'>${board.contents}</a></td>
    <td>${board.createdDate}</td>
    <td>${board.viewCount}</td>
  </tr>
</c:forEach>  
</table>

<script>
(function() {
  $('#pageSize').val('${pageSize}')
})();


$('#pageSize').change((e) => {
  location.href = "list?pageSize=" + $(e.target).val();
});

</script>








