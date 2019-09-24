package com.eomcs.lms.controller;

import java.sql.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

@Controller
public class LessonController {

  @Resource
  private LessonDao lessonDao;

  @RequestMapping("/lesson/add")
  public String add(HttpServletRequest request, HttpServletResponse response) 
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
  
  @RequestMapping("/lesson/delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    
    int no = Integer.parseInt(request.getParameter("no"));
    if (lessonDao.delete(no) == 0) {
      throw new Exception("해당 데이터가 없습니다.");
    }
    return "redirect:list";
  }
  
  @RequestMapping("/lesson/detail")
  public String detail(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    Lesson lesson = lessonDao.findBy(no);
    if (lesson == null) {
      throw new Exception("해당 번호의 데이터가 없습니다!");
    }

    request.setAttribute("lesson", lesson);
    return "/jsp/lesson/detail.jsp";
  }
  
  @RequestMapping("/lesson/list")
  public String list(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    List<Lesson> lessons = lessonDao.findAll();
    request.setAttribute("lessons", lessons);
    return "/jsp/lesson/list.jsp";
  }
  
  @RequestMapping("/lesson/update")
  public String update(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    Lesson lesson = new Lesson();
    lesson.setNo(Integer.parseInt(request.getParameter("no")));
    lesson.setTitle(request.getParameter("title"));
    lesson.setContents(request.getParameter("contents"));
    lesson.setStartDate(Date.valueOf(request.getParameter("startDate")));
    lesson.setEndDate(Date.valueOf(request.getParameter("endDate")));
    lesson.setTotalHours(Integer.parseInt(request.getParameter("totalHours")));
    lesson.setDayHours(Integer.parseInt(request.getParameter("dayHours")));

    lessonDao.update(lesson);

    return "redirect:list";
  }
}












