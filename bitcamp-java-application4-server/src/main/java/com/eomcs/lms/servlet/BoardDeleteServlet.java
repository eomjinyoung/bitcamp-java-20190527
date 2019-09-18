package com.eomcs.lms.servlet;

import java.io.IOException;
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
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    try {
      int no = Integer.parseInt(request.getParameter("no"));
      if (boardDao.delete(no) == 0) {
        throw new Exception("해당 데이터가 없습니다.");
      }
      response.sendRedirect("/board/list");
      
    } catch (Exception e) {
      request.setAttribute("message", "데이터 삭제에 실패했습니다!");
      request.setAttribute("refresh", "/board/list");
      request.setAttribute("error", e);
      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}
