package com.eomcs.lms;

import java.sql.Date;

// 수업 데이터를 저장할 설계도를 작성한다.
public class Lesson {
  
  // 수업 데이터를 저장할 메모리 지정한다.
  // => new 명령을 실행해야만 아래의 변수들이 준비된다.
  int no;
  String title;
  String contents;
  Date startDate;
  Date endDate;
  int totalHours;
  int dayHours;
}
