package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

@WebServlet("/board/detail")
public class BoardDetailServlet extends HttpServlet {
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
    out.println("<html><head><title>게시물 상세</title>"
        + "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>"
        + "<link rel='stylesheet' href='/css/common.css'>"
        + "</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);
    
    out.println("<div id='content'>");
    out.println("<h1>게시물 상세</h1>");
    try {
      int no = Integer.parseInt(request.getParameter("no"));
      Board board = boardDao.findBy(no);
      
      if (board == null) {
        out.println("<p>해당 번호의 데이터가 없습니다!</p>");

      } else {
        out.println("<form action='/board/update' method='post'>");
        out.printf("번호 : <input type='text' name='no' value='%d' readonly><br>\n",
            board.getNo());
        out.printf("내용 : <textarea name='contents' rows='5'"
            + " cols='50'>%s</textarea><br>\n",
            board.getContents());
        out.printf("등록일: %s<br>\n", board.getCreatedDate());
        out.printf("조회수: %d<br>\n", board.getViewCount());
        out.println("<button>변경</button>");
        out.printf("<a href='/board/delete?no=%d'>삭제</a>\n", board.getNo());
        out.println("</form>");
        boardDao.increaseViewCount(no);
      }
      
    } catch (Exception e) {
      out.println("<p>데이터 조회에 실패했습니다!</p>");
      throw new RuntimeException(e);
      
    } finally {
      out.println("</div>");
      request.getRequestDispatcher("/footer").include(request, response);
      out.println("</body></html>");
    }
  }
}
