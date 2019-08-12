// client-v36_1 : DAO Proxy 클래스 대신 DBMS를 사용하는 DAO로 대체한다.  
package com.eomcs.lms;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.dao.mariadb.BoardDaoImpl;
import com.eomcs.lms.dao.mariadb.LessonDaoImpl;
import com.eomcs.lms.dao.mariadb.MemberDaoImpl;
import com.eomcs.lms.handler.BoardAddCommand;
import com.eomcs.lms.handler.BoardDeleteCommand;
import com.eomcs.lms.handler.BoardDetailCommand;
import com.eomcs.lms.handler.BoardListCommand;
import com.eomcs.lms.handler.BoardUpdateCommand;
import com.eomcs.lms.handler.Command;
import com.eomcs.lms.handler.LessonAddCommand;
import com.eomcs.lms.handler.LessonDeleteCommand;
import com.eomcs.lms.handler.LessonDetailCommand;
import com.eomcs.lms.handler.LessonListCommand;
import com.eomcs.lms.handler.LessonUpdateCommand;
import com.eomcs.lms.handler.MemberAddCommand;
import com.eomcs.lms.handler.MemberDeleteCommand;
import com.eomcs.lms.handler.MemberDetailCommand;
import com.eomcs.lms.handler.MemberListCommand;
import com.eomcs.lms.handler.MemberUpdateCommand;
import com.eomcs.util.Input;

public class App {

  Scanner keyScan;

  private void service() {
    // Command 객체가 사용할 데이터 처리 객체를 준비한다.
    BoardDao boardDao = new BoardDaoImpl();
    MemberDao memberDao = new MemberDaoImpl();
    LessonDao lessonDao = new LessonDaoImpl();

    keyScan = new Scanner(System.in);

    Deque<String> commandStack = new ArrayDeque<>();
    Queue<String> commandQueue = new LinkedList<>();

    Input input = new Input(keyScan);

    HashMap<String,Command> commandMap = new HashMap<>();

    commandMap.put("/lesson/add", new LessonAddCommand(input, lessonDao));
    commandMap.put("/lesson/delete", new LessonDeleteCommand(input, lessonDao));
    commandMap.put("/lesson/detail", new LessonDetailCommand(input, lessonDao));
    commandMap.put("/lesson/list", new LessonListCommand(input, lessonDao));
    commandMap.put("/lesson/update", new LessonUpdateCommand(input, lessonDao));

    commandMap.put("/member/add", new MemberAddCommand(input, memberDao));
    commandMap.put("/member/delete", new MemberDeleteCommand(input, memberDao));
    commandMap.put("/member/detail", new MemberDetailCommand(input, memberDao));
    commandMap.put("/member/list", new MemberListCommand(input, memberDao));
    commandMap.put("/member/update", new MemberUpdateCommand(input, memberDao));

    commandMap.put("/board/add", new BoardAddCommand(input, boardDao));
    commandMap.put("/board/delete", new BoardDeleteCommand(input, boardDao));
    commandMap.put("/board/detail", new BoardDetailCommand(input, boardDao));
    commandMap.put("/board/list", new BoardListCommand(input, boardDao));
    commandMap.put("/board/update", new BoardUpdateCommand(input, boardDao));

    while (true) {

      String command = prompt();

      if (command.length() == 0)
        continue;

      commandStack.push(command); 
      commandQueue.offer(command); 

      Command executor = commandMap.get(command);

      if (command.equals("quit")) {
        break;
        
      } else if (command.equals("history")) {
        printCommandHistory(commandStack);

      } else if (command.equals("history2")) {
        printCommandHistory(commandQueue);

      } else if (executor != null) {
        executor.execute();

      } else {
        System.out.println("해당 명령을 지원하지 않습니다!");
      }

      System.out.println();
    } //while
  }

  private void printCommandHistory(Iterable<String> list) {
    Iterator<String> iterator = list.iterator();
    int count = 0;
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
      if (++count % 5 == 0) {
        System.out.print(":");
        if (keyScan.nextLine().equalsIgnoreCase("q"))
          break;
      }
    }
  }

  private String prompt() {
    System.out.print("명령> ");
    return keyScan.nextLine();
  }
  
  public static void main(String[] args) {
    App app = new App();
    app.service();
  }
}










