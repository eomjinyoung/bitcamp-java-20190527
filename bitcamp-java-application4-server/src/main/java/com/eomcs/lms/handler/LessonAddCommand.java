package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.util.Component;
import com.eomcs.util.Input;
import com.eomcs.util.RequestMapping;

@Component("/lesson/add")
public class LessonAddCommand {

  private LessonDao lessonDao;

  public LessonAddCommand(LessonDao lessonDao) {
    this.lessonDao = lessonDao;
  }

  @RequestMapping // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void execute(BufferedReader in, PrintStream out) {
    try {
      Lesson lesson = new Lesson();
      
      lesson.setTitle(Input.getStringValue(in, out, "수업명? "));
      lesson.setContents(Input.getStringValue(in, out, "설명? "));
      lesson.setStartDate(Input.getDateValue(in, out, "시작일? "));
      lesson.setEndDate(Input.getDateValue(in, out, "종료일? "));
      lesson.setTotalHours(Input.getIntValue(in, out, "총수업시간? "));
      lesson.setDayHours(Input.getIntValue(in, out, "일수업시간? "));
      
      lessonDao.insert(lesson);
      out.println("저장하였습니다.");
      
    } catch (Exception e) {
      out.println("데이터 저장에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }

}












