// 애플리케이션 메인 클래스
// => 애플리케이션을 실행할 때 이 클래스를 실행한다.
package com.eomcs.lms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
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
import com.eomcs.util.Input;

public class App {
  
  static Scanner keyScan;
  
  // Command 객체가 사용할 Collection 준비
  static ArrayList<Lesson> lessonList = new ArrayList<>();
  static LinkedList<Member> memberList = new LinkedList<>();
  static ArrayList<Board> boardList = new ArrayList<>();

  public static void main(String[] args) {
    
    // 이전에 저장된 애플리케이션 데이터를 로딩한다.
    loadLessonData();
    loadMemberData();
    loadBoardData();
    
    keyScan = new Scanner(System.in);
    
    // 명령어를 저장하는 컬렉션(collection)
    // => java.util.Stack 에서는 Vector 클래스의 Iterator를 리턴한다.
    //    Vector에서 제공하는 Iterator는 입력한 순서대로 값을 꺼낸다.
    //    즉 FIFO 방식으로 꺼내기 때문에 스택의 LIFO 방식과 맞지 않다.
    //    그래서 ArrayDeque를 사용하는 것이다.
    //    ArrayDeque에서 제공하는 Iterator는 LIFO 방식으로 값을 꺼낸다.
    //
    Deque<String> commandStack = new ArrayDeque<>();
    Queue<String> commandQueue = new LinkedList<>();

    // Input 생성자를 통해 Input이 의존하는 객체인 Scanner를 주입한다.
    Input input = new Input(keyScan);
    
    // Command 객체를 보관할 Map 준비
    HashMap<String,Command> commandMap = new HashMap<>();
    

    // 각 핸들러의 생성자를 통해 의존 객체 "Input"을 주입한다.
    // => 이렇게 어떤 객체가 필요로 하는 의존 객체를 주입하는 것을 
    //    "의존성 주입(Dependency Injection; DI)"라고 한다.
    // => DI를 전문적으로 처리해주는 프레임워크가 있으니 그 이름도 유명한 
    //    "Spring IoC 컨테이너"!
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
      
      // 사용자가 아무것도 입력하지 않았으면 다시 입력 받는다.
      if (command.length() == 0)
        continue;
      
      commandStack.push(command); // 사용자가 입력한 명령을 보관한다.
      commandQueue.offer(command); // 사용자가 입력한 명령을 보관한다.
      
      // 사용자가 입력한 명령어를 처리할 Command 객체를 Map에서 꺼낸다.
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
    
    // 애플리케이션의 실행을 종료하기 전에 데이터를 저장한다.
    saveLessonData();
    saveMemberData();
    saveBoardData();
  }

  private static void printCommandHistory(Iterable<String> list) {
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
  
  private static String prompt() {
    System.out.print("명령> ");
    return keyScan.nextLine();
  }
  
  private static void loadLessonData() {
    File file = new File("./lesson.dat");
    
    // 바이트 단위로 출력된 데이터를 읽을 객체를 준비한다.
    FileInputStream in = null; 
    DataInputStream in2 = null;
    
    try {
      in = new FileInputStream(file);
      
      // 바이트 배열을 읽어 원래의 타입인 int나 String 등으로 변환해주는 도구를 
      // FileInputStream에 붙인다.
      in2 = new DataInputStream(in);
      
      // 파일에서 첫 번째 int 값을 먼저 읽는다.
      // 이 값은 파일에 저장된 데이터의 개수이다. 
      int len = in2.readInt();
      
      while (len-- > 0) {
        Lesson lesson = new Lesson();
        
        // 파일에서 데이터의 각 항목을 읽어 객체에 저장한다.
        lesson.setNo(in2.readInt());
        lesson.setTitle(in2.readUTF());
        lesson.setContents(in2.readUTF());
        lesson.setStartDate(Date.valueOf(in2.readUTF()));
        lesson.setEndDate(Date.valueOf(in2.readUTF()));
        lesson.setTotalHours(in2.readInt());
        lesson.setDayHours(in2.readInt());
        
        lessonList.add(lesson);
      }
      
    } catch (FileNotFoundException e) {
      System.out.println("읽을 파일을 찾을 수 없습니다!");
      
    } catch (Exception e) {
      System.out.println("파일을 읽는 중에 오류가 발생했습니다!");
      
    } finally {
      try {in2.close();} catch (Exception e) {}
      try {in.close();} catch (Exception e) {}
    }
  }
  
  private static void saveLessonData() {
    
    File file = new File("./lesson.dat");
    
    // 바이트 단위로 데이터를 다루기 위해 바이트 스트림 클래스를 준비한다.
    FileOutputStream out = null;
    DataOutputStream out2 = null;
    
    try {
      out = new FileOutputStream(file);
      
      // FileOutputStream은 바이트 또는 바이트 배열을 출력할 수 있다.
      // 따라서 어떤 값을 출력하려면 byte 배열로 만들어야 한다.
      // 자바는 이것을 도와주는 DataOutputStream 이라는 클래스를 제공하고 있다.
      // 이 클래스를 FileOutputStream에 붙여서 사용하라!
      out2 = new DataOutputStream(out);
      
      // 본격적으로 데이터를 출력하기 전에 몇 개의 데이터를 출력할 것인지 먼저 그 개수를 출력한다.
      out2.writeInt(lessonList.size());
      
      for (Lesson lesson : lessonList) {
        // 수업 데이터의 각 항목을 바이트 배열로 변환하여 출력한다.
        // DataOutputStream 클래스에 그런 기능을 제공하는 메서드가 있다.
        out2.writeInt(lesson.getNo()); // int --> byte[]
        out2.writeUTF(lesson.getTitle()); // String --> byte[]
        out2.writeUTF(lesson.getContents()); // String --> byte[]
        out2.writeUTF(lesson.getStartDate().toString()); // String --> byte[]
        out2.writeUTF(lesson.getEndDate().toString()); // String --> byte[]
        out2.writeInt(lesson.getTotalHours()); // int --> byte[]
        out2.writeInt(lesson.getDayHours()); // int --> byte[]
      }
    } catch (FileNotFoundException e) {
      System.out.println("파일을 생성할 수 없습니다!");

    } catch (IOException e) {
      System.out.println("파일에 데이터를 출력하는 중에 오류 발생!");
      
    } finally {
      try {out2.close();} catch (Exception e) {}
      try {out.close();} catch (Exception e) {} 
    }
  }
  
  private static void loadMemberData() {
    File file = new File("./member.dat");
    
    FileInputStream in = null; 
    DataInputStream in2 = null;
    
    try {
      in = new FileInputStream(file);
      in2 = new DataInputStream(in);
      
      int len = in2.readInt();
      
      while (len-- > 0) {
        Member member = new Member();
        
        member.setNo(in2.readInt());
        member.setName(in2.readUTF());
        member.setEmail(in2.readUTF());
        member.setPassword(in2.readUTF());
        member.setPhoto(in2.readUTF());
        member.setTel(in2.readUTF());
        member.setRegisteredDate(Date.valueOf(in2.readUTF()));
        
        memberList.add(member);
      }
      
    } catch (FileNotFoundException e) {
      System.out.println("읽을 파일을 찾을 수 없습니다!");
      
    } catch (Exception e) {
      System.out.println("파일을 읽는 중에 오류가 발생했습니다!");
      
    } finally {
      try {in2.close();} catch (Exception e) {}
      try {in.close();} catch (Exception e) {}
    }
    
  }
  
  private static void saveMemberData() {
    File file = new File("./member.dat");
    
    FileOutputStream out = null;
    DataOutputStream out2 = null;
    
    try {
      out = new FileOutputStream(file);
      out2 = new DataOutputStream(out);
      
      out2.writeInt(memberList.size());

      for (Member member : memberList) {
        out2.writeInt(member.getNo());
        out2.writeUTF(member.getName());
        out2.writeUTF(member.getEmail());
        out2.writeUTF(member.getPassword());
        out2.writeUTF(member.getPhoto());
        out2.writeUTF(member.getTel());
        out2.writeUTF(member.getRegisteredDate().toString());
      }
    } catch (FileNotFoundException e) {
      System.out.println("파일을 생성할 수 없습니다!");

    } catch (IOException e) {
      System.out.println("파일에 데이터를 출력하는 중에 오류 발생!");
      
    } finally {
      try {out2.close();} catch (Exception e) {}
      try {out.close();} catch (Exception e) {} 
    }
  }
  
  private static void loadBoardData() {
    File file = new File("./board.dat");
    
    FileInputStream in = null; 
    DataInputStream in2 = null;
    
    try {
      in = new FileInputStream(file);
      in2 = new DataInputStream(in);
      
      int len = in2.readInt();
      
      while (len-- > 0) {
        Board board = new Board();
        
        board.setNo(in2.readInt());
        board.setContents(in2.readUTF());
        board.setViewCount(in2.readInt());
        board.setCreatedDate(Date.valueOf(in2.readUTF()));
        
        boardList.add(board);
      }
      
    } catch (FileNotFoundException e) {
      System.out.println("읽을 파일을 찾을 수 없습니다!");
      
    } catch (Exception e) {
      System.out.println("파일을 읽는 중에 오류가 발생했습니다!");
      
    } finally {
      try {in2.close();} catch (Exception e) {}
      try {in.close();} catch (Exception e) {}
    }
    
  }
  
  private static void saveBoardData() {
    
    File file = new File("./board.dat");
    
    FileOutputStream out = null;
    DataOutputStream out2 = null;
    
    try {
      out = new FileOutputStream(file);
      out2 = new DataOutputStream(out);
      
      out2.writeInt(boardList.size());
      
      for (Board board : boardList) {
        out2.writeInt(board.getNo());
        out2.writeUTF(board.getContents());
        out2.writeInt(board.getViewCount());
        out2.writeUTF(board.getCreatedDate().toString());
      }
    } catch (FileNotFoundException e) {
      System.out.println("파일을 생성할 수 없습니다!");

    } catch (IOException e) {
      System.out.println("파일에 데이터를 출력하는 중에 오류 발생!");
      
    } finally {
      try {out2.close();} catch (Exception e) {}
      try {out.close();} catch (Exception e) {} 
    }
  }
}










