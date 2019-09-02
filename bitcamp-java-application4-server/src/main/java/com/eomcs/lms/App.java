// v48_1 : 애노테이션으로 호출될 메서드를 지정하기
package com.eomcs.lms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.eomcs.util.ApplicationContext;
import com.eomcs.util.RequestMapping;
import com.eomcs.util.SqlSessionFactoryProxy;

public class App {

  private static final int CONTINUE = 1;
  private static final int STOP = 0;

  ApplicationContext appCtx;
  int state;
  
  // 스레드풀
  ExecutorService executorService = Executors.newCachedThreadPool();
  
  public App() throws Exception {
    // 처음에는 클라이언트 요청을 처리해야 하는 상태로 설정한다.
    state = CONTINUE;
    appCtx = new ApplicationContext("com.eomcs.lms");
  }

  @SuppressWarnings("static-access")
  private void service() {

    try (ServerSocket serverSocket = new ServerSocket(8888);) {
      System.out.println("애플리케이션 서버가 시작되었음!");

      while (true) {
        // 클라이언트가 접속하면 작업을 수행할 Runnable 객체를 만들어 스레드풀에 맡긴다.
        executorService.submit(new CommandProcessor(serverSocket.accept()));
        
        // 한 클라이언트가 serverstop 명령을 보내면 종료 상태로 설정되고 
        // 다음 요청을 처리할 때 즉시 실행을 멈춘다.
        if (state == STOP)
          break;
      }

      // 스레드풀에게 실행 종료를 요청한다.
      // => 스레드풀은 자신이 관리하는 스레드들이 실행이 종료되었는지 감시한다.
      executorService.shutdown();
      
      // 스레드풀이 관리하는 모든 스레드가 종료되었는지 매 0.5초마다 검사한다.
      // => 스레드풀의 모든 스레드가 실행을 종료했으면 즉시 main 스레드를 종료한다.
      while (!executorService.isTerminated()) {
        Thread.currentThread().sleep(500);
      }
      
      System.out.println("애플리케이션 서버를 종료함!");

    } catch (Exception e) {
      System.out.println("소켓 통신 오류!");
      e.printStackTrace();
    }
  }

  class CommandProcessor implements Runnable {
    
    Socket socket;
    
    public CommandProcessor(Socket socket) {
      this.socket = socket;
    }
    
    @Override
    public void run() {
      try (Socket socket = this.socket;
          BufferedReader in = new BufferedReader(
              new InputStreamReader(socket.getInputStream()));
          PrintStream out = new PrintStream(socket.getOutputStream())) {

        System.out.println("클라이언트와 연결됨!");

        // 클라이언트가 보낸 명령을 읽는다.
        String request = in.readLine();
        if (request.equals("quit")) {
          out.println("Good bye!");
          
        } else if (request.equals("serverstop")) {
          state = STOP;
          out.println("Good bye!");
          
        } else {
          try {
            Object command = appCtx.getBean(request);
            Method requestHandler = getRequestHandler(command);
            if (requestHandler != null) {
              requestHandler.invoke(command, in, out);
            } else {
              throw new Exception("요청을 처리할 메서드가 없습니다.");
            }
            
          } catch (Exception e) {
            out.println("해당 명령을 처리할 수 없습니다.");
            e.printStackTrace();
          }
        }
        out.println("!end!");
        out.flush();

        System.out.println("클라이언트와 연결 끊음!");

      } catch (Exception e) {
        System.out.println("클라이언트와 통신 오류!");
        
      } finally {
        // 현재 스레드가 클라이언트 요청에 대해 응답을 완료했다면,
        // 현재 스레드에 보관된 Mybatis의 SqlSession 객체를 제거해야 한다.
        // 그래야만 다음 클라이언트 요청이 들어 왔을 때 
        // 새 SqlSession 객체를 사용할 것이다.
        SqlSessionFactoryProxy proxy = 
            (SqlSessionFactoryProxy) appCtx.getBean("sqlSessionFactory");
        proxy.clearSession();
      }
    }

    // 클래스의 메서드 중에서 @RequestMapping이 붙은 메서드를 찾아낸다.
    private Method getRequestHandler(Object command) {
      // 요청을 처리하기 위해 호출할 메서드를 찾아낸다.
      // => 클래스의 public 메서드 목록을 꺼낸다.
      Method[] methods = command.getClass().getMethods();
      for (Method m : methods) {
        RequestMapping anno = m.getAnnotation(RequestMapping.class);
        if (anno != null) {
          return m;
        }
      }
      return null;
    }
  }
  
  public static void main(String[] args) {
    try {
      App app = new App();
      app.service();

    } catch (Exception e) {
      System.out.println("시스템 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }
}










