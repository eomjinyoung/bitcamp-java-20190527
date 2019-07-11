package com.eomcs.lms.handler;

import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.util.Input;

public class LessonHandler {
  
  private Lesson[] lessons = new Lesson[100];
  private int lessonsSize = 0;
  
  public Input input;
  
  public LessonHandler(Input input) {
    this.input = input;
  }
  
  public void addLesson() {
    // 수업 데이터를 저장할 메모리를 Lesson 설계도에 따라 만든다.
    Lesson lesson = new Lesson();
    
    // 사용자가 입력한 값을 Lesson 인스턴스의 각 변수에 저장한다.
    lesson.no = input.getIntValue("번호? ");
    lesson.title = input.getStringValue("수업명? ");
    lesson.contents = input.getStringValue("설명? ");
    lesson.startDate = input.getDateValue("시작일? ");
    lesson.endDate = input.getDateValue("종료일? ");
    lesson.totalHours = input.getIntValue("총수업시간? ");
    lesson.dayHours = input.getIntValue("일수업시간? ");
    
    // 수업 데이터를 저장하고 있는 인스턴스의 주소를 레퍼런스 배열에 저장한다.
    lessons[lessonsSize++] = lesson;
    
    System.out.println("저장하였습니다.");
  }

  public void listLesson() {
    for (int i = 0; i < lessonsSize; i++) {
      Lesson lesson = lessons[i];
      System.out.printf("%s, %s, %s ~ %s, %s\n", 
          lesson.no, lesson.title, 
          lesson.startDate, lesson.endDate, lesson.totalHours);
    }
  }
  
}
