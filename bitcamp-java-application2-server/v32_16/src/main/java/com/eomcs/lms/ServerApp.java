// v32_16: 옵저버 패턴을 적용하여 서버를 시작할 때 데이터 로딩, 서버를 종료할 때 데이터 저장을 수행하자.
package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import com.eomcs.lms.context.ServletContextListener;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.servlet.BoardServlet;
import com.eomcs.lms.servlet.LessonServlet;
import com.eomcs.lms.servlet.MemberServlet;

public class ServerApp {

  ArrayList<ServletContextListener> listeners = new ArrayList<>();
  int port;
  
  public ServerApp(int port) {
    this.port = port;
  }
  
  public void execute() {
    System.out.println("[수업관리시스템 서버 애플리케이션]");

    try (ServerSocket serverSocket = new ServerSocket(this.port)) {
      System.out.println("서버 시작!");

      // 서버가 실행되는 동안 공유할 객체를 보관하는 저장소를 준비한다.
      HashMap<String,Object> servletContext = new HashMap<>();
      
      // 서버가 시작되면 보고를 받을 관찰자(observer)에게 보고한다.
      for (ServletContextListener listener : listeners) {
        listener.contextInitialized(servletContext);
      }
      
      try (Socket clientSocket = serverSocket.accept();
          ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
          ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
        
        System.out.println("클라이언트와 연결되었음.");
        
        BoardDao boardDao = (BoardDao)servletContext.get("boardDao");
        MemberDao memberDao = (MemberDao)servletContext.get("memberDao");
        LessonDao lessonDao = (LessonDao)servletContext.get("lessonDao");
        
        BoardServlet boardServlet = new BoardServlet(boardDao, in, out);
        MemberServlet memberServlet = new MemberServlet(memberDao, in, out);
        LessonServlet lessonServlet = new LessonServlet(lessonDao, in, out);
        
        while (true) {
          // 클라이언트가 보낸 명령을 읽는다.
          String command = in.readUTF();
          System.out.println(command + " 요청 처리중...");
          
          if (command.startsWith("/board/")) {
            boardServlet.service(command);
            
          } else if (command.startsWith("/member/")) {
            memberServlet.service(command);
            
          } else if (command.startsWith("/lesson/")) {
            lessonServlet.service(command);
            
          } else if (command.equals("quit")) {
            out.writeUTF("ok");
            out.flush();
            break;
            
          } else {
            out.writeUTF("fail");
            out.writeUTF("지원하지 않는 명령입니다.");
          }
          out.flush();
          System.out.println("클라이언트에게 응답 완료!");
        } // loop:
      } 
      
      System.out.println("클라이언트와 연결을 끊었음.");
      
      // 서버가 종료될 때 관찰자(observer)에게 보고한다.
      for (ServletContextListener listener : listeners) {
        listener.contextDestroyed(servletContext);
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    System.out.println("서버 종료!");
  }
  
  // 서버가 시작하거나 종료할 때 보고를 받을 객체를 등록하는 메서드
  // => 즉 서블릿을 실행하는데 필요한 환경을 준비시키는 객체를 등록한다.
  //
  public void addServletContextListener(ServletContextListener listener) {
    listeners.add(listener);
  }
  
  public static void main(String[] args) {
    
    ServerApp server = new ServerApp(8888);
    
    // 서버의 시작과 종료 상태를 보고 받을 객체를 등록한다.
    // => 보고를 받는 객체는 ServletContextListener 규칙에 따라 만든 클래스여야 한다.
    server.addServletContextListener(new AppInitListener());
    
    server.execute();
  }
}






