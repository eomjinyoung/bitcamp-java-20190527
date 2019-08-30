package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.util.Input;

public class LessonUpdateCommand implements Command {

  private LessonDao lessonDao;

  public LessonUpdateCommand(LessonDao lessonDao) {
    this.lessonDao = lessonDao;
  }
  
  public String getCommandName() {
    return "/lesson/update";
  }
  
  

  @Override
  public void execute(BufferedReader in, PrintStream out) {
    try {
      int no = Input.getIntValue(in, out, "번호? ");

      Lesson lesson = lessonDao.findBy(no);
      if (lesson == null) {
        out.println("해당 번호의 데이터가 없습니다!");
        return;
      }

      // 사용자로부터 변경할 값을 입력 받는다.
      Lesson data = new Lesson();
      data.setNo(no);
      
      String str = Input.getStringValue(in, out, "수업명(" + lesson.getTitle() + ")? ");
      if (str.length() > 0) {
        data.setTitle(str);
      }

      str = Input.getStringValue(in, out, "수업내용? ");
      if (str.length() > 0) {
        data.setContents(str);
      }
      
      try {
        data.setStartDate(
          Input.getDateValue(in, out, "시작일(" + lesson.getStartDate() + ")? "));
      } catch (Exception e) {
        // 클라이언트가 보낸 날짜가 유효하지 않으면 무시
      }
      
      try {
        data.setEndDate(
          Input.getDateValue(in, out, "종료일(" + lesson.getEndDate() + ")? "));
      } catch (Exception e) {
        // 클라이언트가 보낸 날짜가 유효하지 않으면 무시
      }
      
      try {
        data.setTotalHours(
          Input.getIntValue(in, out, "총수업시간(" + lesson.getTotalHours() + ")? "));
      } catch (Exception e) {
        // 클라이언트가 보낸 값이 숫자가 아니라면 무시
      }
      
      try {
        data.setDayHours(
          Input.getIntValue(in, out, "일수업시간(" + lesson.getDayHours() + ")? "));
      } catch (Exception e) {
        // 클라이언트가 보낸 값이 숫자가 아니라면 무시
      }

      lessonDao.update(data);
      
      out.println("데이터를 변경하였습니다.");

    } catch (Exception e) {
      out.println("데이터 변경에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }

}












