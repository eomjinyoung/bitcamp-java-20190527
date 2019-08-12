package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import com.eomcs.lms.servlet.Servlet;

// 별도의 실행 흐름을 만들기 위해서는 Thread 클래스를 상속 받아야 한다.
// 또는 Runnable 자격을 갖춘 후 Thread로 실행해야 한다.
public class RequestHandler extends Thread {
  
  public static boolean isStopping = false;
  
  Socket socket;
  Map<String,Object> servletContext;
  
  // 클라이언트가 접속을 하면 그 소켓 정보를 가지고 이 클래스의 인스턴스를 생성한다.
  // => 그런 후 스레드를 분리하여 시작시키면, run()이 실행된다.
  public RequestHandler(Socket socket, Map<String,Object> servletContext) {
    this.socket = socket;
    this.servletContext = servletContext;
  }
  
  // 별도의 실행 흐름(실;스레드)은 run() 메서드에 작성한다.
  @Override
  public void run() {
    // 여기에서 클라이언트의 요청을 처리한다.
    // main thread와는 분리된 실행 흐름(thread)이기 때문에 
    // 여기에서 실행이 지체되더라도 main thread의 흐름에는 영향을 끼치지 않는다.
    try (Socket clientSocket = this.socket;
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

      System.out.println("클라이언트와 연결되었음.");

      // 클라이언트가 보낸 명령을 읽는다.
      String command = in.readUTF();
      System.out.println(command + " 요청 처리중...");

      Servlet servlet = null;
      
      if (command.equals("serverstop")) {
        isStopping = true;
        return;
        
      } else if ((servlet = findServlet(command)) != null) {
        servlet.service(command, in, out);
        
      } else {
        out.writeUTF("fail");
        out.writeUTF("지원하지 않는 명령입니다.");
      }

      out.flush();
      System.out.println("클라이언트에게 응답 완료!");

    } catch (Exception e) {
      System.out.println("클라이언트와의 통신 오류! - " + e.getMessage());
    }
    
    System.out.println("클라이언트와 연결을 끊었음.");
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

}
