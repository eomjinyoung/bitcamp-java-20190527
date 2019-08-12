package com.eomcs.lms.handler;

import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.util.Input;

public class LessonDetailCommand implements Command {
  
  private LessonDao lessonDao;
  private Input input;
  
  public LessonDetailCommand(Input input, LessonDao lessonDao) {
    this.input = input;
    this.lessonDao = lessonDao;
  }

  @Override
  public void execute() {
    int no = input.getIntValue("번호? ");
    
    try {
      Lesson lesson = lessonDao.findBy(no);
      if (lesson == null) {
        System.out.println("해당 번호의 데이터가 없습니다!");
        return;
      }
      
      System.out.printf("수업명: %s\n", lesson.getTitle());
      System.out.printf("수업내용: %s\n", lesson.getContents());
      System.out.printf("기간: %s ~ %s\n", lesson.getStartDate(), lesson.getEndDate());
      System.out.printf("총수업시간: %d\n", lesson.getTotalHours());
      System.out.printf("일수업시간: %d\n", lesson.getDayHours());
      
    } catch (Exception e) {
      System.out.println("데이터 조회에 실패했습니다!");
      System.out.println(e.getMessage());
    }
    
    
  }

}












