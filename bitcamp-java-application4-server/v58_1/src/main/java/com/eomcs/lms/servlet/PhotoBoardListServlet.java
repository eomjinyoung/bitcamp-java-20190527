package com.eomcs.lms.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.domain.PhotoBoard;

@WebServlet("/photoboard/list")
public class PhotoBoardListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private PhotoBoardDao photoBoardDao;
  
  @Override
  public void init() throws ServletException {
    ApplicationContext appCtx = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
    photoBoardDao = appCtx.getBean(PhotoBoardDao.class);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    
    try {
      List<PhotoBoard> photoBoards = photoBoardDao.findAll();
      request.setAttribute("photoBoards", photoBoards);
      request.setAttribute("viewUrl", "/jsp/photoboard/list.jsp");
      
    } catch (Exception e) {
      request.setAttribute("error", e);
    }
  }
}
