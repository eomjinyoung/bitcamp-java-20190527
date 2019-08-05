// client-v32_2 : 
package com.eomcs.lms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import com.eomcs.lms.context.ApplicationContextListener;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.Member;
import com.eomcs.lms.handler.BoardAddCommand;
import com.eomcs.lms.handler.BoardDeleteCommand;
import com.eomcs.lms.handler.BoardDetailCommand;
import com.eomcs.lms.handler.BoardListCommand;
import com.eomcs.lms.handler.BoardUpdateCommand;
import com.eomcs.lms.handler.CalcPlusCommand;
import com.eomcs.lms.handler.Command;
import com.eomcs.lms.handler.HiCommand;
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
import com.eomcs.lms.listener.DataLoaderListener;
import com.eomcs.lms.listener.HelloApplicationContextListener;
import com.eomcs.util.Input;

public class App {

  // 옵저버를 보관할 컬렉션 객체 준비
  ArrayList<ApplicationContextListener> appCtxListeners = new ArrayList<>();
  
  // App 객체가 사용할 값을 모아두는 바구니 준비
  Map<String,Object> beanContainer = new HashMap<>(); 
  
  Scanner keyScan;
  
  
  @SuppressWarnings("unchecked")
  private void service() {
    
    // 애플리케이션의 서비스를 시작할 때 등록된 옵저버에게 알린다.
    for (ApplicationContextListener listener : appCtxListeners) {
      listener.contextInitialized(beanContainer);
    }
    
    // 옵저버에게 보고한 후 옵저버가 준비한 객체를 꺼낸다.
    List<Lesson> lessonList = (List<Lesson>)beanContainer.get("lessonList");
    List<Member> memberList = (List<Member>)beanContainer.get("memberList");
    List<Board> boardList = (List<Board>)beanContainer.get("boardList");
    
    keyScan = new Scanner(System.in);
    
    Deque<String> commandStack = new ArrayDeque<>();
    Queue<String> commandQueue = new LinkedList<>();

    Input input = new Input(keyScan);
    
    HashMap<String,Command> commandMap = new HashMap<>();
    
    commandMap.put("/lesson/add", new LessonAddCommand(input, lessonList));
    commandMap.put("/lesson/delete", new LessonDeleteCommand(input, lessonList));
    commandMap.put("/lesson/detail", new LessonDetailCommand(input, lessonList));
    commandMap.put("/lesson/list", new LessonListCommand(input, lessonList));
    commandMap.put("/lesson/update", new LessonUpdateCommand(input, lessonList));
    
    commandMap.put("/member/add", new MemberAddCommand(input, memberList));
    commandMap.put("/member/delete", new MemberDeleteCommand(input, memberList));
    commandMap.put("/member/detail", new MemberDetailCommand(input, memberList));
    commandMap.put("/member/list", new MemberListCommand(input, memberList));
    commandMap.put("/member/update", new MemberUpdateCommand(input, memberList));
    
    commandMap.put("/board/add", new BoardAddCommand(input, boardList));
    commandMap.put("/board/delete", new BoardDeleteCommand(input, boardList));
    commandMap.put("/board/detail", new BoardDetailCommand(input, boardList));
    commandMap.put("/board/list", new BoardListCommand(input, boardList));
    commandMap.put("/board/update", new BoardUpdateCommand(input, boardList));
    
    commandMap.put("/hi", new HiCommand(input));
    commandMap.put("/calc/plus", new CalcPlusCommand(input));
    
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
    
    // 애플리케이션의 서비스를 종료할 때 등록된 옵저버에게 알린다.
    for (ApplicationContextListener listener : appCtxListeners) {
      listener.contextDestroyed(beanContainer);
    }
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
  

  
  // ApplicationContextListener 옵저버를 등록하는 메서드
  public void addApplicationContextListener(ApplicationContextListener listener) {
    this.appCtxListeners.add(listener);
  }
  
  public static void main(String[] args) {
    App app = new App();
    
    // 애플리케이션을 시작하거나 종료할 때 보고를 받고자 하는 객체를 등록한다.
    app.addApplicationContextListener(new HelloApplicationContextListener());
    app.addApplicationContextListener(new DataLoaderListener());
    
    app.service();
  }
}










