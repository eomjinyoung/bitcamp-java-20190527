package com.eomcs.lms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

@Component("/lesson/detail")
public class LessonDetailController implements PageController {

  @Resource
  private LessonDao lessonDao;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Lesson lesson = lessonDao.findBy(no);
    if (lesson == null) {
      throw new Exception("해당 번호의 데이터가 없습니다!");
    }

    request.setAttribute("lesson", lesson);
    return "/jsp/lesson/detail.jsp";
  }
}












