package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private BoardDao boardDao;
  
  @Override
  public void init() throws ServletException {
    ApplicationContext appCtx = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
    boardDao = appCtx.getBean(BoardDao.class);
  }
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>게시물 목록</title>"
        + "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>"
        + "<link rel='stylesheet' href='/css/common.css'>"
        + "</head>");
    out.println("<body>");
    
    request.getRequestDispatcher("/header").include(request, response);
    
    out.println("<div id='content'>");
    out.println("<h1>게시물 목록</h1>");
    out.println("<a href='/board/add'>새 글</a><br>");
    try {
      out.println("<table class='table table-hover'>");
      out.println("<tr><th>번호</th><th>내용</th><th>등록일</th><th>조회수</th></tr>");
      List<Board> boards = boardDao.findAll();
      for (Board board : boards) {
        out.printf("<tr><td>%d</td>"
            + "<td><a href='/board/detail?no=%d'>%s</a></td>"
            + "<td>%s</td><td>%d</td></tr>\n", 
            board.getNo(),
            board.getNo(),
            board.getContents(), 
            board.getCreatedDate(), 
            board.getViewCount());
      }
      out.println("</table>");
      
    } catch (Exception e) {
      out.println("<p>데이터 목록 조회에 실패했습니다!</p>");
      throw new RuntimeException(e);
      
    } finally {
      out.println("</div>");
      request.getRequestDispatcher("/footer").include(request, response);
      out.println("</body></html>");
    }
  }
}
