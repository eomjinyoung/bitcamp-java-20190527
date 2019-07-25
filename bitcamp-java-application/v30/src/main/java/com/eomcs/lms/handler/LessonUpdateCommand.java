package com.eomcs.lms.handler;

import java.util.List;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.util.Input;

public class LessonUpdateCommand implements Command {
  
  private List<Lesson> list;
  private Input input;
  
  public LessonUpdateCommand(Input input, List<Lesson> list) {
    this.input = input;
    this.list = list;
  }

  @Override
  public void execute() {
    int no = input.getIntValue("번호? ");
    
    // 사용자가 입력한 번호를 가지고 목록에서 그 번호에 해당하는 Lesson 객체를 찾는다.
    Lesson lesson = null;
    for (int i = 0; i < list.size(); i++) {
      Lesson temp = list.get(i);
      if (temp.getNo() == no) {
        lesson = temp;
        break;
      }
    }
    
    if (lesson == null) {
      System.out.println("해당 번호의 데이터가 없습니다!");
      return;
    }
    
    // 사용자로부터 변경할 값을 입력 받는다.
    String str = input.getStringValue("수업명(" + lesson.getTitle() + ")? ");
    if (str.length() > 0) {
      lesson.setTitle(str);
    }
    
    str = input.getStringValue("수업내용? ");
    if (str.length() > 0) {
      lesson.setContents(str);
    }
    
    lesson.setStartDate(
        input.getDateValue("시작일(" + lesson.getStartDate() + ")? "));
    
    lesson.setEndDate(
        input.getDateValue("종료일(" + lesson.getEndDate() + ")? "));
    
    lesson.setTotalHours(
        input.getIntValue("총수업시간(" + lesson.getTotalHours() + ")? "));
    
    lesson.setDayHours(
        input.getIntValue("일수업시간(" + lesson.getDayHours() + ")? "));
    
    System.out.println("데이터를 변경하였습니다.");
  }

}












