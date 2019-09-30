package com.eomcs.lms.web;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

@Controller
@RequestMapping("/lesson")
public class LessonController {

  @Resource
  private LessonDao lessonDao;

  @GetMapping("form")
  public void form() {
  }
  
  @PostMapping("add")
  public String add(Lesson lesson) 
      throws Exception {
    lessonDao.insert(lesson);
    return "redirect:list";
  }
  
  @GetMapping("delete")
  public String delete(int no) 
      throws Exception {
    if (lessonDao.delete(no) == 0) {
      throw new Exception("해당 데이터가 없습니다.");
    }
    return "redirect:list";
  }
  
  @GetMapping("detail")
  public void detail(Model model, int no) 
      throws Exception {

    Lesson lesson = lessonDao.findBy(no);
    if (lesson == null) {
      throw new Exception("해당 번호의 데이터가 없습니다!");
    }

    model.addAttribute("lesson", lesson);
  }
  
  @GetMapping("list")
  public void list(Model model) 
      throws Exception {
    List<Lesson> lessons = lessonDao.findAll();
    model.addAttribute("lessons", lessons);
  }
  
  @PostMapping("update")
  public String update(Lesson lesson) 
      throws Exception {
    lessonDao.update(lesson);
    return "redirect:list";
  }
}












