package com.eomcs.lms.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

@Controller
public class LessonController {

  @Resource
  private LessonDao lessonDao;

  @RequestMapping("/lesson/form")
  public String form() {
    return "/jsp/lesson/form.jsp";
  }
  
  @RequestMapping("/lesson/add")
  public String add(Lesson lesson) 
      throws Exception {
    lessonDao.insert(lesson);
    return "redirect:list";
  }
  
  @RequestMapping("/lesson/delete")
  public String delete(int no) 
      throws Exception {
    if (lessonDao.delete(no) == 0) {
      throw new Exception("해당 데이터가 없습니다.");
    }
    return "redirect:list";
  }
  
  @RequestMapping("/lesson/detail")
  public String detail(Map<String,Object> model, int no) 
      throws Exception {

    Lesson lesson = lessonDao.findBy(no);
    if (lesson == null) {
      throw new Exception("해당 번호의 데이터가 없습니다!");
    }

    model.put("lesson", lesson);
    return "/jsp/lesson/detail.jsp";
  }
  
  @RequestMapping("/lesson/list")
  public String list(Map<String,Object> model) 
      throws Exception {
    List<Lesson> lessons = lessonDao.findAll();
    model.put("lessons", lessons);
    return "/jsp/lesson/list.jsp";
  }
  
  @RequestMapping("/lesson/update")
  public String update(Lesson lesson) 
      throws Exception {
    lessonDao.update(lesson);
    return "redirect:list";
  }
}












