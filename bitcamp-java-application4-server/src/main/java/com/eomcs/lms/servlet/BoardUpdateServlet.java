package com.eomcs.lms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

@WebServlet("/board/update")
public class BoardUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private BoardDao boardDao;
  
  @Override
  public void init() throws ServletException {
    ApplicationContext appCtx = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
    boardDao = appCtx.getBean(BoardDao.class);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    try {
      Board board = new Board();
      board.setNo(Integer.parseInt(request.getParameter("no")));
      board.setContents(request.getParameter("contents"));
      boardDao.update(board);
      response.sendRedirect("/board/list");
      
    } catch (Exception e) {
      request.setAttribute("message", "데이터 변경에 실패했습니다!");
      request.setAttribute("refresh", "/board/list");
      request.setAttribute("error", e);
      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}
