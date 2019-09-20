package com.eomcs.lms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

@WebServlet("/lesson/detail")
public class LessonDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private LessonDao lessonDao;

  @Override
  public void init() throws ServletException {
    ApplicationContext appCtx = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
    lessonDao = appCtx.getBean(LessonDao.class);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    
    response.setContentType("text/html;charset=UTF-8");
    try {
      int no = Integer.parseInt(request.getParameter("no"));
     
      Lesson lesson = lessonDao.findBy(no);
      if (lesson == null) {
        throw new Exception("해당 번호의 데이터가 없습니다!");
      }
      
      request.setAttribute("lesson", lesson);
      request.getRequestDispatcher("/jsp/lesson/detail.jsp").include(request, response);
      
    } catch (Exception e) {
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "/lesson/list");
      request.setAttribute("error", e);
      request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
    }
  }
}












