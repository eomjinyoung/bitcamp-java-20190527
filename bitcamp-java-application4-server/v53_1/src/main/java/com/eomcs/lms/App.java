// v53_1 : log4j 1.2.x 적용하기
package com.eomcs.lms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.util.RequestMappingHandlerMapping;
import com.eomcs.util.RequestMappingHandlerMapping.RequestHandler;

public class App {

  // Log4j의 로그 출력 도구를 준비한다.
  private static final Logger logger = LogManager.getLogger(App.class);
  
  private static final int CONTINUE = 1;
  private static final int STOP = 0;

  ApplicationContext appCtx;
  RequestMappingHandlerMapping handlerMapping;
  int state;
  
  // 스레드풀
  ExecutorService executorService = Executors.newCachedThreadPool();
  
  public App() throws Exception {
    // 처음에는 클라이언트 요청을 처리해야 하는 상태로 설정한다.
    state = CONTINUE; 
    appCtx = new AnnotationConfigApplicationContext(AppConfig.class);
    
    // Spring IoC 컨테이너에 들어 있는(Spring IoC 컨테이너가 생성한) 객체 알아내기
    String[] beanNames = appCtx.getBeanDefinitionNames();
    logger.debug("[Spring IoC 컨테이너 객체들]------------");
    for (String beanName : beanNames) {
      logger.debug(String.format("%s(%s)",
          appCtx.getBean(beanName).getClass().getSimpleName(),
          beanName));
    }
    logger.debug("------------------------------------");
    
    handlerMapping = createRequestMappingHandlerMapping();
  }

  private RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
    
    RequestMappingHandlerMapping mapping = 
        new RequestMappingHandlerMapping();
    
    // 객체풀에서 @Component 애노테이션이 붙은 객체 목록을 꺼낸다.
    Map<String,Object> components = appCtx.getBeansWithAnnotation(Component.class);
    
    logger.trace("[요청 핸들러] ===========================");
    
    // 객체 안에 선언된 메서드 중에서 @RequestMapping이 붙은 메서드를 찾아낸다.
    Collection<Object> objList = components.values();
    objList.forEach(obj -> {
      
      Method[] methods = null;
      
      if (AopUtils.isAopProxy(obj)) { // 원본이 아니라 프록시 객체라면
        try {
          // 프록시 객체의 클래스가 아니라 원본 객체의 클래스 정보를 가져온다.
          Class<?> originClass = 
              (Class<?>) obj.getClass().getMethod("getTargetClass").invoke(obj);
          methods = originClass.getMethods();
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else { // 원본 객체일 경우,
        // 원본 객체의 클래스로부터 메서드 목록을 가져온다.
        methods = obj.getClass().getMethods();
      }
      
      for (Method m : methods) {
        RequestMapping anno = m.getAnnotation(RequestMapping.class);
        if (anno == null)
          continue;
        // @RequestMapping 이 붙은 메서드를 찾으면 mapping 객체에 보관한다.
        mapping.addRequestHandler(anno.value()[0], obj, m);
        logger.trace(String.format("%s ==> %s.%s()", anno.value()[0], 
            obj.getClass().getSimpleName(),
            m.getName()));
      }
      
    });
    
    return mapping;
  }

  @SuppressWarnings("static-access")
  private void service() {

    try (ServerSocket serverSocket = new ServerSocket(8888);) {
      logger.info("애플리케이션 서버가 시작되었음!");

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
      
      logger.info("애플리케이션 서버를 종료함!");

    } catch (Exception e) {
      logger.info("소켓 통신 오류!");
      
      StringWriter out = new StringWriter();
      e.printStackTrace(new PrintWriter(out));
      logger.debug(out.toString());
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

        logger.info("클라이언트와 연결됨!");

        // 클라이언트가 보낸 명령을 읽는다.
        String request = in.readLine();
        if (request.equals("quit")) {
          out.println("Good bye!");
          
        } else if (request.equals("serverstop")) {
          state = STOP;
          out.println("Good bye!");
          
        } else {
          try {
            RequestHandler requestHandler = 
                handlerMapping.getRequestHandler(request);

            if (requestHandler != null) {
              requestHandler.method.invoke(requestHandler.bean, in, out);
            } else {
              throw new Exception("요청을 처리할 메서드가 없습니다.");
            }
            
          } catch (Exception e) {
            logger.info("해당 명령을 처리할 수 없습니다.");
            
            StringWriter out2 = new StringWriter();
            e.printStackTrace(new PrintWriter(out2));
            logger.debug(out2.toString());
          }
        }
        out.println("!end!");
        out.flush();

        logger.info("클라이언트와 연결 끊음!");

      } catch (Exception e) {
        logger.info("클라이언트와 통신 오류!");
        
      } 
    }
  }
  
  public static void main(String[] args) {
    try {
      App app = new App();
      app.service();

    } catch (Exception e) {
      logger.info("시스템 실행 중 오류 발생!");
      
      StringWriter out2 = new StringWriter();
      e.printStackTrace(new PrintWriter(out2));
      logger.debug(out2.toString());
    }
  }
}










