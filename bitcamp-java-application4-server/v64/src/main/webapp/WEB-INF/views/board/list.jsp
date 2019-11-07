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

<nav aria-label="Page navigation example">
  <ul class="pagination">
    <li class="page-item" data-page="prev">
      <a class="page-link" href="#">
        <span aria-hidden="true">&laquo;</span> 
      </a>
    </li>
<c:forEach begin="${beginPage}" end="${endPage}" var="page">
    <li class="page-item" data-page="${page}">
      <a class="page-link" ${page != pageNo ? "href=#" : ""}>${page}</a>
    </li>
</c:forEach>
    <li class="page-item" data-page="next">
      <a class="page-link" href="#">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>

<script>
(function() {
  $('#pageSize').val('${pageSize}')
})();

$('#pageSize').change((e) => {
  location.href = "list?pageSize=" + $(e.target).val();
});

var currentPage = ${pageNo};

$('.page-item').click((e) => {
  e.preventDefault();
  // e.currentTarget? 리스너가 호출될 때, 그 리스너가 등록된 태그를 가르킨다.
  // e.target? 이벤트가 발생된 원천 태그이다. 
  //var page = e.currentTarget.getAttribute('data-page');
  var page = $(e.currentTarget).attr('data-page');
  if (page == "prev") {
    if (currentPage == 1)
      return;
    location.href = "list?pageNo=" + (currentPage - 1) + "&pageSize=" + ${pageSize};
    
  } else if (page == "next") {
    if (currentPage >= ${totalPage})
      return
    location.href = "list?pageNo=" + (currentPage + 1) + "&pageSize=" + ${pageSize};
  
  } else {
    location.href = "list?pageNo=" + page + "&pageSize=" + ${pageSize};
  }
});

</script>








