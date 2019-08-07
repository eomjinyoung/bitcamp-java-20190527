// v33_3: 클라이언트 요청 마다 서블릿 객체를 만들지 않기
package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import com.eomcs.lms.context.ServletContextListener;
import com.eomcs.lms.servlet.Servlet;

public class ServerApp {

  ArrayList<ServletContextListener> listeners = new ArrayList<>();
  int port;
  
  //서버가 실행되는 동안 공유할 객체를 보관하는 저장소를 준비한다.
  HashMap<String,Object> servletContext = new HashMap<>();

  public ServerApp(int port) {
    this.port = port;
  }

  public void execute() {
    System.out.println("[수업관리시스템 서버 애플리케이션]");

    try (ServerSocket serverSocket = new ServerSocket(this.port)) {
      System.out.println("서버 시작!");

      // 서버가 시작되면 보고를 받을 관찰자(observer)에게 보고한다.
      for (ServletContextListener listener : listeners) {
        listener.contextInitialized(servletContext);
      }

      while (true) {
        try (Socket clientSocket = serverSocket.accept();
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

          System.out.println("클라이언트와 연결되었음.");

          // 클라이언트가 보낸 명령을 읽는다.
          String command = in.readUTF();
          System.out.println(command + " 요청 처리중...");

          Servlet servlet = null;
          
          if (command.equals("serverstop")) {
            break;
            
          } else if ((servlet = findServlet(command)) != null) {
            servlet.service(command, in, out);
            
          } else {
            out.writeUTF("fail");
            out.writeUTF("지원하지 않는 명령입니다.");
          }

          out.flush();
          System.out.println("클라이언트에게 응답 완료!");

        } 

        System.out.println("클라이언트와 연결을 끊었음.");
      } // while

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

  private Servlet findServlet(String command) {
    Set<String> keys = servletContext.keySet();
    
    // 명령어에 포함된 키를 찾아서, 해당 키로 저장된 서블릿을 꺼낸다.
    // => 명령(/board/list) : 키(/board/)
    for (String key : keys) {
      if (command.startsWith(key)) {
        return (Servlet)servletContext.get(key);
      }
    }
    // => 명령(/files/list) : 키(?)
    return null;
  }
  
  public static void main(String[] args) {

    ServerApp server = new ServerApp(8888);

    // 서버의 시작과 종료 상태를 보고 받을 객체를 등록한다.
    // => 보고를 받는 객체는 ServletContextListener 규칙에 따라 만든 클래스여야 한다.
    server.addServletContextListener(new AppInitListener());

    server.execute();
  }
}






