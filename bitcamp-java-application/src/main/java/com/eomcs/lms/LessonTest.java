package com.eomcs.lms;

import java.sql.Date;

public class LessonTest {
  public static void main(String[] args) {
    
    // 수업 데이터를 저장하기 위해 낱개의 메모리를 준비해서 사용하는 경우:
    int no = 1;
    String title = "자바";
    String contents = "자바 개발";
    Date startDate = Date.valueOf("2019-1-1");
    Date endDate = Date.valueOf("2019-2-2");
    int totalHours = 100;
    int dayHours = 4;
    
    // 수업 데이터를 저장하기 위해 미리 준비한 메모리 설계도를 이용하는 경우
    // => Lesson 메모리 설계도(클래스)에 따라 메모리를 준비하기
    Lesson lesson = new Lesson();
    
    // => 레퍼런스에 저장된 주소로 찾아가서 메모리에 값 넣기
    lesson.no = 2;
    lesson.title = "웹 프로그래밍";
    lesson.contents = "웹 개발자";
    lesson.startDate = Date.valueOf("2019-2-2");
    lesson.endDate = Date.valueOf("2019-3-3");
    lesson.totalHours = 200;
    lesson.dayHours = 4;
    
    System.out.printf("%s -- %s\n", no, lesson.no);
    System.out.printf("%s -- %s\n", title, lesson.title);
    
    Lesson lesson2 = lesson;
    lesson.title = "오호라!";
    System.out.println(lesson2.title);
    
    
    
  }
}





