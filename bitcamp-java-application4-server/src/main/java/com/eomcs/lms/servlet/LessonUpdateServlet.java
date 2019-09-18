package com.eomcs.lms.servlet;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

@WebServlet("/lesson/update")
public class LessonUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private LessonDao lessonDao;

  @Override
  public void init() throws ServletException {
    ApplicationContext appCtx = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
    lessonDao = appCtx.getBean(LessonDao.class);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    try {
      Lesson lesson = new Lesson();
      lesson.setNo(Integer.parseInt(request.getParameter("no")));
      lesson.setTitle(request.getParameter("title"));
      lesson.setContents(request.getParameter("contents"));
      lesson.setStartDate(Date.valueOf(request.getParameter("startDate")));
      lesson.setEndDate(Date.valueOf(request.getParameter("endDate")));
      lesson.setTotalHours(Integer.parseInt(request.getParameter("totalHours")));
      lesson.setDayHours(Integer.parseInt(request.getParameter("dayHours")));
      
      lessonDao.update(lesson);
      response.sendRedirect("/lesson/list");
      
    } catch (Exception e) {
      request.setAttribute("message", "데이터 변경에 실패했습니다!");
      request.setAttribute("refresh", "/lesson/list");
      request.setAttribute("error", e);
      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}












