package com.eomcs.lms.handler;

import java.util.List;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.util.Input;

public class LessonAddCommand implements Command {
  
  private List<Lesson> list;
  private Input input;
  
  public LessonAddCommand(Input input, List<Lesson> list) {
    this.input = input;
    this.list = list;
  }
  
  @Override
  public void execute() {
    // 수업 데이터를 저장할 메모리를 Lesson 설계도에 따라 만든다.
    Lesson lesson = new Lesson();
    
    // 사용자가 입력한 값을 Lesson 인스턴스의 각 변수에 저장한다.
    lesson.setNo(input.getIntValue("번호? "));
    lesson.setTitle(input.getStringValue("수업명? "));
    lesson.setContents(input.getStringValue("설명? "));
    lesson.setStartDate(input.getDateValue("시작일? "));
    lesson.setEndDate(input.getDateValue("종료일? "));
    lesson.setTotalHours(input.getIntValue("총수업시간? "));
    lesson.setDayHours(input.getIntValue("일수업시간? "));
    
    // LessonHandler에서 직접 데이터를 보관하지 않고 
    // LessonList에게 전달한다.
    list.add(lesson);
    
    System.out.println("저장하였습니다.");
  }

}












