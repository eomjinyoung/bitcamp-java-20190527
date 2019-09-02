package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.List;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.util.Component;
import com.eomcs.util.Input;
import com.eomcs.util.RequestMapping;

@Component
public class LessonCommand {

  private LessonDao lessonDao;

  public LessonCommand(LessonDao lessonDao) {
    this.lessonDao = lessonDao;
  }

  @RequestMapping("/lesson/add") // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void add(BufferedReader in, PrintStream out) {
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
  
  @RequestMapping("/lesson/delete") // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void delete(BufferedReader in, PrintStream out) {
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

  @RequestMapping("/lesson/detail") // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void detail(BufferedReader in, PrintStream out) {
    try {
      int no = Input.getIntValue(in, out, "번호? ");
     
      Lesson lesson = lessonDao.findBy(no);
      if (lesson == null) {
        out.println("해당 번호의 데이터가 없습니다!");
        return;
      }
      
      out.printf("수업명: %s\n", lesson.getTitle());
      out.printf("수업내용: %s\n", lesson.getContents());
      out.printf("기간: %s ~ %s\n", lesson.getStartDate(), lesson.getEndDate());
      out.printf("총수업시간: %d\n", lesson.getTotalHours());
      out.printf("일수업시간: %d\n", lesson.getDayHours());
      
    } catch (Exception e) {
      out.println("데이터 조회에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }
  
  @RequestMapping("/lesson/list") // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void list(BufferedReader in, PrintStream out) {
    try {
      List<Lesson> lessons = lessonDao.findAll();
      for (Lesson lesson : lessons) {
        out.printf("%s, %s, %s ~ %s, %s\n", 
            lesson.getNo(), lesson.getTitle(), 
            lesson.getStartDate(), lesson.getEndDate(), lesson.getTotalHours());
      }
      
    } catch (Exception e) {
      out.println("데이터 목록 조회에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }
  
  @RequestMapping("/lesson/update") // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void update(BufferedReader in, PrintStream out) {
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












