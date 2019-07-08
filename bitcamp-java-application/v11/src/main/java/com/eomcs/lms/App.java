// 애플리케이션 메인 클래스
// => 애플리케이션을 실행할 때 이 클래스를 실행한다.
package com.eomcs.lms;

import java.util.Scanner;
import com.eomcs.lms.handler.BoardHandler;
import com.eomcs.lms.handler.LessonHandler;
import com.eomcs.lms.handler.MemberHandler;
import com.eomcs.lms.util.Input;

public class App {
  
  static Scanner keyScan;
  
  public static void main(String[] args) {
    
    keyScan = new Scanner(System.in);
    
    Input.keyScan = keyScan;
    LessonHandler.keyScan = keyScan;
    MemberHandler.keyScan = keyScan;
    BoardHandler.keyScan = keyScan;
    
    while (true) {
      
      String command = prompt();
      
      if (command.equals("quit")) {
        break;
        
      } else if (command.equals("/lesson/add")) {
        LessonHandler.addLesson(); // addLesson() 메서드 블록에 묶어 놓은 코드를 실행한다.
        
      } else if (command.equals("/lesson/list")) {
        LessonHandler.listLesson();
        
      } else if (command.equals("/member/add")) {
        MemberHandler.addMember();
      
      } else if (command.equals("/member/list")) {
        MemberHandler.listMember();
        
      } else if (command.equals("/board/add")) {
        BoardHandler.addBoard();
        
      } else if (command.equals("/board/list")) {
        BoardHandler.listBoard();
        
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
}










