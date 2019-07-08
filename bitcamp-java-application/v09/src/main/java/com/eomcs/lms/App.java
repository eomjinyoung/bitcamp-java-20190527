// 애플리케이션 메인 클래스
// => 애플리케이션을 실행할 때 이 클래스를 실행한다.
package com.eomcs.lms;

import java.sql.Date;
import java.util.Scanner;

public class App {
  
  static Scanner keyScan;
  
  static Lesson[] lessons = new Lesson[100];
  static int lessonsSize = 0;
  
  static Member[] members = new Member[100];
  static int membersSize = 0;

  static Board[] boards = new Board[100];
  static int boardsSize = 0;
  
  public static void main(String[] args) {
    java.io.InputStream keyboard = System.in;
    keyScan = new Scanner(keyboard);
    
    while (true) {
      
      String command = prompt();
      
      if (command.equals("quit")) {
        break;
        
      } else if (command.equals("/lesson/add")) {
        addLesson(); // addLesson() 메서드 블록에 묶어 놓은 코드를 실행한다.
        
      } else if (command.equals("/lesson/list")) {
        listLesson();
        
      } else if (command.equals("/member/add")) {
        addMember();
      
      } else if (command.equals("/member/list")) {
        listMember();
        
      } else if (command.equals("/board/add")) {
        addBoard();
        
      } else if (command.equals("/board/list")) {
        listBoard();
        
      } else {
        System.out.println("해당 명령을 지원하지 않습니다!");
      }
      
      System.out.println();
    }
  }

  static String prompt() {
    System.out.print("명령> ");
    return keyScan.nextLine();
  }

  static void listBoard() {
    for (int i = 0; i < boardsSize; i++) {
      Board board = boards[i];
      System.out.printf("%s, %s, %s, %s\n", 
          board.no, board.contents, board.createdDate, board.viewCount);
    }
  }

  static void addBoard() {
    Board board = new Board();
    
    board.no = getIntValue("번호? ");
    board.contents = getStringValue("내용? ");
    board.createdDate = new Date(System.currentTimeMillis()); 
    
    boards[boardsSize++] = board;
    System.out.println("저장하였습니다.");
  }

  static void listMember() {
    for (int i = 0; i < membersSize; i++) {
      Member member = members[i];
      System.out.printf("%s, %s, %s, %s, %s\n", 
          member.no, member.name, member.email, 
          member.tel, member.registeredDate);
    }
  }

  static void addMember() {
    Member member = new Member();
    member.no = getIntValue("번호? ");
    member.name = getStringValue("이름? ");
    member.email = getStringValue("이메일? ");
    member.password = getStringValue("암호? ");
    member.photo = getStringValue("사진? ");
    member.tel = getStringValue("전화? ");
    member.registeredDate = new Date(System.currentTimeMillis()); 
      
    members[membersSize++] = member;
    System.out.println("저장하였습니다.");
  }
  
  static void addLesson() {
    // 수업 데이터를 저장할 메모리를 Lesson 설계도에 따라 만든다.
    Lesson lesson = new Lesson();
    
    // 사용자가 입력한 값을 Lesson 인스턴스의 각 변수에 저장한다.
    lesson.no = getIntValue("번호? ");
    lesson.title = getStringValue("수업명? ");
    lesson.contents = getStringValue("설명? ");
    lesson.startDate = getDateValue("시작일? ");
    lesson.endDate = getDateValue("종료일? ");
    lesson.totalHours = getIntValue("총수업시간? ");
    lesson.dayHours = getIntValue("일수업시간? ");
    
    // 수업 데이터를 저장하고 있는 인스턴스의 주소를 레퍼런스 배열에 저장한다.
    lessons[lessonsSize++] = lesson;
    
    System.out.println("저장하였습니다.");
  }

  static void listLesson() {
    for (int i = 0; i < lessonsSize; i++) {
      Lesson lesson = lessons[i];
      System.out.printf("%s, %s, %s ~ %s, %s\n", 
          lesson.no, lesson.title, 
          lesson.startDate, lesson.endDate, lesson.totalHours);
    }
  }
  
  private static int getIntValue(String message) {
    while (true) {
      try {
        System.out.print(message);
        return Integer.parseInt(keyScan.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("숫자를 입력하세요.");
      }
    }
  }
  
  private static Date getDateValue(String message) {
    while (true) {
      try {
        System.out.print(message);
        return Date.valueOf(keyScan.nextLine());
      } catch (IllegalArgumentException e) {
        System.out.println("2019-07-05 형식으로 입력하세요.");
      }
    }
  }
  
  private static String getStringValue(String message) {
    System.out.print(message);
    return keyScan.nextLine();
  }
  
}










