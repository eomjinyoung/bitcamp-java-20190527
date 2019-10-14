package com.eomcs.lms.web.json;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.service.LessonService;

@RestController("json.LessonController")
@RequestMapping("/json/lesson")
public class LessonController {

  @Resource
  private LessonService lessonService;

  @PostMapping("add")
  public JsonResult add(Lesson lesson) throws Exception {
    try {
      lessonService.insert(lesson);
      return new JsonResult().setState(JsonResult.SUCCESS);

    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }

  @GetMapping("delete")
  public JsonResult delete(int no) throws Exception {
    try {
      lessonService.delete(no);
      return new JsonResult().setState(JsonResult.SUCCESS);

    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }

  @GetMapping("detail")
  public JsonResult detail(int no) throws Exception {
    try {
      Lesson lesson = lessonService.get(no);
      return new JsonResult()
          .setState(JsonResult.SUCCESS)
          .setResult(lesson);

    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }

  @GetMapping("list")
  public JsonResult list() throws Exception {
    try {
      List<Lesson> lessons = lessonService.list();
      return new JsonResult()
          .setState(JsonResult.SUCCESS)
          .setResult(lessons);

    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }

  @PostMapping("update")
  public JsonResult update(Lesson lesson) throws Exception {
    try {
      lessonService.update(lesson);
      return new JsonResult().setState(JsonResult.SUCCESS);

    } catch (Exception e) {
      return new JsonResult()
          .setState(JsonResult.FAILURE)
          .setMessage(e.getMessage());
    }
  }
}












