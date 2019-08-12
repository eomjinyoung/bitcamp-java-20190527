// v34_1: 클라이언트 요청을 처리하는 부분을 별도의 스레드(실)로 분리하여 실행한다.
package com.eomcs.lms;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import com.eomcs.lms.context.ServletContextListener;

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
        System.out.println("클라이언트 요청을 기다리는 중...");
        
        // 클라이언트 요청이 들어오면 클라이언트와 통신할 때 사용할 소켓을 생성한다.
        Socket socket = serverSocket.accept();
        
        
        /*
        // 그리고 소켓을 이용하여 클라이언트 요청을 처리할 객체를 준비한다.
        RequestHandler requestHandler = new RequestHandler(socket, servletContext);
        
        // 단, RequestHandler의 작업은 별도의 실행 흐름으로 분리하여 실행시킨다.
        // => RequestHandler는 별도의 실행흐름으로 분리될 수 있는 Thread 기능을 상속 받았다.
        requestHandler.start();
        */
        
        // 위의 두 문장을 다음 한 문장으로 처리한다.
        new RequestHandler(socket, servletContext).start();
        // 스레드를 분리하여 실행시키면 main thread는 즉시 다음 명령을 실행한다.
        
        // 클라이언트 중에 하나가 서버 종료를 설정했다면, 현재 접속한 클라이언트 요청까지만 처리하고 
        // 서버 실행을 멈춘다.
        // 주의! 
        // main() 메서드 호출이 종료되었다 하더라도 실행 중인 스레드가 있으면 
        // JVM이 완전히 종료되지 않는다.
        // 그러니 분리된 스레드가 실행하는 도중에 main() 호출이 종료되어 
        // 해당 스레드의 작업이 중간에 방해 받을 것이라는 걱정은 하지 말라!
        if (RequestHandler.isStopping)
          break;
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

  public static void main(String[] args) {

    ServerApp server = new ServerApp(8888);

    // 서버의 시작과 종료 상태를 보고 받을 객체를 등록한다.
    // => 보고를 받는 객체는 ServletContextListener 규칙에 따라 만든 클래스여야 한다.
    server.addServletContextListener(new AppInitListener());

    server.execute();
  }
}






