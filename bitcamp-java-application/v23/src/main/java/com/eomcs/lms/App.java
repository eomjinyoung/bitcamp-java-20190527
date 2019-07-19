// 애플리케이션 메인 클래스
// => 애플리케이션을 실행할 때 이 클래스를 실행한다.
package com.eomcs.lms;

import java.util.Scanner;
import com.eomcs.lms.handler.BoardHandler;
import com.eomcs.lms.handler.LessonHandler;
import com.eomcs.lms.handler.MemberHandler;
import com.eomcs.util.ArrayList;
import com.eomcs.util.Input;
import com.eomcs.util.LinkedList;
import com.eomcs.util.Queue;
import com.eomcs.util.Stack;

public class App {
  
  static Scanner keyScan;
  static Stack<String> commandStack = new Stack<>();
  static Queue<String> commandQueue = new Queue<>();
  
  public static void main(String[] args) throws Exception {
    
    keyScan = new Scanner(System.in);
    
    // Input 생성자를 통해 Input이 의존하는 객체인 Scanner를 주입한다.
    Input input = new Input(keyScan);
    
    // 각 핸들러의 생성자를 통해 의존 객체 "Input"을 주입한다.
    // => 이렇게 어떤 객체가 필요로 하는 의존 객체를 주입하는 것을 
    //    "의존성 주입(Dependency Injection; DI)"라고 한다.
    // => DI를 전문적으로 처리해주는 프레임워크가 있으니 그 이름도 유명한 
    //    "Spring IoC 컨테이너"!
    LessonHandler lessonHandler = new LessonHandler(input, new ArrayList<>());
    MemberHandler memberHandler = new MemberHandler(input, new LinkedList<>());
    BoardHandler boardHandler = new BoardHandler(input, new ArrayList<>());
    BoardHandler boardHandler2 = new BoardHandler(input, new LinkedList<>());

    while (true) {
      
      String command = prompt();
      
      commandStack.push(command); // 사용자가 입력한 명령을 보관한다.
      commandQueue.offer(command); // 사용자가 입력한 명령을 보관한다.
      
      if (command.equals("quit")) {
        break;
      } else if (command.equals("history")) {
        printCommandHistory();
        
      } else if (command.equals("history2")) {
        printCommandHistory2();
        
      } else if (command.equals("/lesson/add")) {
        lessonHandler.addLesson(); // addLesson() 메서드 블록에 묶어 놓은 코드를 실행한다.
        
      } else if (command.equals("/lesson/list")) {
        lessonHandler.listLesson();
        
      } else if (command.equals("/lesson/detail")) {
        lessonHandler.detailLesson();
        
      } else if (command.equals("/lesson/update")) {
        lessonHandler.updateLesson();
        
      } else if (command.equals("/lesson/delete")) {
        lessonHandler.deleteLesson();
        
      } else if (command.equals("/member/add")) {
        memberHandler.addMember();
      
      } else if (command.equals("/member/list")) {
        memberHandler.listMember();
        
      } else if (command.equals("/member/detail")) {
        memberHandler.detailMember();
        
      } else if (command.equals("/member/update")) {
        memberHandler.updateMember();
        
      } else if (command.equals("/member/delete")) {
        memberHandler.deleteMember();
        
      } else if (command.equals("/board/add")) {
        boardHandler.addBoard();
        
      } else if (command.equals("/board/list")) {
        boardHandler.listBoard();
        
      } else if (command.equals("/board/detail")) {
        boardHandler.detailBoard();
        
      } else if (command.equals("/board/update")) {
        boardHandler.updateBoard();
        
      } else if (command.equals("/board/delete")) {
        boardHandler.deleteBoard();
        
      } else if (command.equals("/board2/add")) {
        boardHandler2.addBoard();
        
      } else if (command.equals("/board2/list")) {
        boardHandler2.listBoard();
        
      } else if (command.equals("/board2/detail")) {
        boardHandler2.detailBoard();
        
      } else if (command.equals("/board2/update")) {
        boardHandler2.updateBoard();
        
      } else if (command.equals("/board2/delete")) {
        boardHandler2.deleteBoard();
        
      } else {
        System.out.println("해당 명령을 지원하지 않습니다!");
      }
      
      System.out.println();
    }
  }

  private static void printCommandHistory() throws Exception {
    Stack<String> stack = commandStack.clone();
    int count = 0;
    while (!stack.empty()) {
      System.out.println(stack.pop());
      if (++count % 5 == 0) {
        System.out.print(":");
        if (keyScan.nextLine().equalsIgnoreCase("q"))
          break;
      }
    }
  }
  
  private static void printCommandHistory2() throws Exception {
    Queue<String> queue = commandQueue.clone();
    int count = 0;
    while (!queue.empty()) {
      System.out.println(queue.poll());
      if (++count % 5 == 0) {
        System.out.print(":");
        if (keyScan.nextLine().equalsIgnoreCase("q"))
          break;
      }
    }
  }

  private static String prompt() {
    System.out.print("명령> ");
    return keyScan.nextLine();
  }
}










