package com.eomcs.lms.handler;

import com.eomcs.lms.dao.LessonDao;
import com.eomcs.util.Input;

public class LessonDeleteCommand implements Command {
  
  private LessonDao lessonDao;
  private Input input;
  
  public LessonDeleteCommand(Input input, LessonDao lessonDao) {
    this.input = input;
    this.lessonDao = lessonDao;
  }

  @Override
  public void execute() {
    int no = input.getIntValue("번호? ");
    
    try {
      lessonDao.delete(no);
      System.out.println("삭제하였습니다.");
      
    } catch (Exception e) {
      System.out.println("데이터 삭제에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }
  
  
  
}












