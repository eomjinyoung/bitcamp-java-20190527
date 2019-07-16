package com.eomcs.lms.handler;

import com.eomcs.lms.domain.Lesson;
import com.eomcs.util.ArrayList;
import com.eomcs.util.Input;

public class LessonHandler {
  
  private ArrayList<Lesson> lessonList = new ArrayList<>();
  private Input input;
  
  public LessonHandler(Input input) {
    this.input = input;
  }
  
  public void addLesson() {
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
    lessonList.add(lesson);
    
    System.out.println("저장하였습니다.");
  }

  public void listLesson() {
    Lesson[] lessons = lessonList.toArray(new Lesson[] {});
    for (Lesson lesson : lessons) {
      System.out.printf("%s, %s, %s ~ %s, %s\n", 
          lesson.getNo(), lesson.getTitle(), 
          lesson.getStartDate(), lesson.getEndDate(), lesson.getTotalHours());
    }
  }

  public void detailLesson() {
    int no = input.getIntValue("번호? ");
    
    // 사용자가 입력한 번호를 가지고 목록에서 그 번호에 해당하는 Lesson 객체를 찾는다.
    Lesson lesson = null;
    for (int i = 0; i < lessonList.size(); i++) {
      Lesson temp = lessonList.get(i);
      if (temp.getNo() == no) {
        lesson = temp;
        break;
      }
    }
    
    if (lesson == null) {
      System.out.println("해당 번호의 데이터가 없습니다!");
      return;
    }
    
    System.out.printf("수업명: %s\n", lesson.getTitle());
    System.out.printf("수업내용: %s\n", lesson.getContents());
    System.out.printf("기간: %s ~ %s\n", lesson.getStartDate(), lesson.getEndDate());
    System.out.printf("총수업시간: %d\n", lesson.getTotalHours());
    System.out.printf("일수업시간: %d\n", lesson.getDayHours());
  }

  public void updateLesson() {
    int no = input.getIntValue("번호? ");
    
    // 사용자가 입력한 번호를 가지고 목록에서 그 번호에 해당하는 Lesson 객체를 찾는다.
    Lesson lesson = null;
    for (int i = 0; i < lessonList.size(); i++) {
      Lesson temp = lessonList.get(i);
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












