package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.util.Component;
import com.eomcs.util.Input;
import com.eomcs.util.RequestMapping;

@Component("/lesson/delete")
public class LessonDeleteCommand {
  
  private LessonDao lessonDao;
  
  public LessonDeleteCommand(LessonDao lessonDao) {
    this.lessonDao = lessonDao;
  }

  @RequestMapping // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void execute(BufferedReader in, PrintStream out) {
    try {

      int no = Input.getIntValue(in, out, "번호? ");
      
      if (lessonDao.delete(no) > 0) {
        out.println("삭제하였습니다.");
      } else {
        out.println("해당 데이터가 없습니다.");
      }
      
    } catch (Exception e) {
      out.println("데이터 삭제에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }
  
  
  
}












