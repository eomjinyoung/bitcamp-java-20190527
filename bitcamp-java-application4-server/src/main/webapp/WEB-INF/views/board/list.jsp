<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>게시물 목록</h1>

<div class="btn-toolbar justify-content-between" role="toolbar">
  <div class="btn-group btn-group-sm" role="group">
    <a href="form" class="btn btn-primary">새 글</a>
  </div>
  <div class="input-group">
    <select id="pageSize">
      <option value="3">3</option>
      <option value="8">8</option>
      <option value="10">10</option>
      <option value="20">20</option>
    </select>
  </div>
</div>

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








