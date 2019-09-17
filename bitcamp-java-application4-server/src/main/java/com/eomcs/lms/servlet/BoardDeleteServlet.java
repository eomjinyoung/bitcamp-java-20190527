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

@WebServlet("/board/delete")
public class BoardDeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private BoardDao boardDao;
  
  @Override
  public void init() throws ServletException {
    ApplicationContext appCtx = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
    boardDao = appCtx.getBean(BoardDao.class);
  }
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>게시물 삭제</title>"
        + "<meta http-equiv='Refresh' content='1;url=/board/list'>"
        + "</head>");
    out.println("<body><h1>게시물 삭제</h1>");
    try {
      int no = Integer.parseInt(request.getParameter("no"));
      if (boardDao.delete(no) > 0) {
        out.println("<p>데이터를 삭제하였습니다.</p>");
      } else {
        out.println("<p>해당 데이터가 없습니다.</p>");
      }
      
    } catch (Exception e) {
      out.println("<p>데이터 삭제에 실패했습니다!</p>");
      throw new RuntimeException(e);
      
    } finally {
      out.println("</body></html>");
    }
  }
}
