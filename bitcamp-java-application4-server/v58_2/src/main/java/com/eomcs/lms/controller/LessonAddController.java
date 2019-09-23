package com.eomcs.lms.controller;

import java.sql.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

@Component("/lesson/add")
public class LessonAddController implements PageController {

  @Resource
  private LessonDao lessonDao;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    if (request.getMethod().equalsIgnoreCase("GET")) {
      return "/jsp/lesson/form.jsp";
    }

    Lesson lesson = new Lesson();
    lesson.setTitle(request.getParameter("title"));
    lesson.setContents(request.getParameter("contents"));
    lesson.setStartDate(Date.valueOf(request.getParameter("startDate")));
    lesson.setEndDate(Date.valueOf(request.getParameter("endDate")));
    lesson.setTotalHours(Integer.parseInt(request.getParameter("totalHours")));
    lesson.setDayHours(Integer.parseInt(request.getParameter("dayHours")));

    lessonDao.insert(lesson);

    return "redirect:list";
  }
}












