package com.eomcs.lms.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

@Component("/lesson/list")
public class LessonListController implements PageController {

  @Resource
  private LessonDao lessonDao;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    List<Lesson> lessons = lessonDao.findAll();
    request.setAttribute("lessons", lessons);
    return "/jsp/lesson/list.jsp";
  }
}












