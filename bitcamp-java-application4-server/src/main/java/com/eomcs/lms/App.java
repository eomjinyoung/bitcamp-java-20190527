// v54_1 : Apache의 HttpClient를 이용하여 HTTP 서버 만들기
package com.eomcs.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.SocketTimeoutException;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.util.RequestMappingHandlerMapping;
import com.eomcs.util.RequestMappingHandlerMapping.RequestHandler;
import com.eomcs.util.ServletRequest;
import com.eomcs.util.ServletResponse;

public class App implements HttpRequestHandler {

  // Log4j의 로그 출력 도구를 준비한다.
  private static final Logger logger = LogManager.getLogger(App.class);

  ApplicationContext appCtx;
  RequestMappingHandlerMapping handlerMapping;
  int state;

  public App() throws Exception {
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
  private void service() throws Exception {

    SocketConfig socketConfig = SocketConfig.custom()
        .setSoTimeout(15000)
        .setTcpNoDelay(true)
        .build();

    final HttpServer server = ServerBootstrap.bootstrap()
        .setListenerPort(8888)
        .setServerInfo("Bitcamp/1.1")
        .setSocketConfig(socketConfig)
        .setSslContext(null)
        .setExceptionLogger(ex -> {
          if (ex instanceof SocketTimeoutException) {
            System.err.println("Connection timed out");
          } else if (ex instanceof ConnectionClosedException) {
            System.err.println(ex.getMessage());
          } else {
            ex.printStackTrace();
          }
        })
        .registerHandler("*", this)
        .create();

    server.start();
    logger.info("서버 실행 중...");

    server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        server.shutdown(5, TimeUnit.SECONDS);
      }
    });
  }

  @Override
  public void handle(HttpRequest request, HttpResponse response, HttpContext context)
      throws HttpException, IOException {

    // 클라이언트가 요청한 방식을 알아 낸다.
    String method = request.getRequestLine().getMethod().toUpperCase(Locale.ROOT);
    if (!method.equals("GET")) { // GET 요청이 아니라면 오류 내용을 응답한다.
      throw new MethodNotSupportedException(method + " method not supported");
    }

    // 커맨드 객체에 있는 request handler를 호출할 때 넘겨 줄 파라미터 객체 준비
    ServletRequest servletRequest = new ServletRequest();
    ServletResponse servletResponse = new ServletResponse();
    
    // 클라이언트가 요청한 명령 알아내기
    // [request line]
    // => GET /member/add?name=aaa&email=aaa@test.com&password=1111&tel=1111-1111 HTTP/1.1
    // [uri]
    // => /member/add?name=aaa&email=aaa@test.com&password=1111&tel=1111-1111
    String uriStr = request.getRequestLine().getUri();
    String[] values = uriStr.split("\\?");
    
    // => /member/add
    String command = values[0];
    logger.info(command);
    
    if (values.length > 1) {
      // => name=aaa&email=aaa@test.com&password=1111&tel=1111-1111
      String queryString = values[1]; // 출력 용.
      logger.info(queryString);
    }
    
    try {
      RequestHandler requestHandler = 
          handlerMapping.getRequestHandler(command);

      if (requestHandler != null) {
        // 클라이언트 요청을 처리하기 위해 메서드를 호출한다.
        servletRequest.setUri(uriStr); // URL에 포함된 파라미터 값을 추출하여 보관한다.
        requestHandler.method.invoke(requestHandler.bean, 
            servletRequest, servletResponse);

        // 클라이언트에게 응답
        response.setStatusCode(HttpStatus.SC_OK);
        StringEntity entity = new StringEntity(
            servletResponse.getResponseEntity(),
            ContentType.create("text/html", "UTF-8"));
        response.setEntity(entity);
        logger.info("성공!");

      } else {
        response.setStatusCode(HttpStatus.SC_NOT_FOUND);
        StringEntity entity = new StringEntity(
            "<html><body><h1>해당 명령을 찾을 수 없습니다.</h1></body></html>",
            ContentType.create("text/html", "UTF-8"));
        response.setEntity(entity);
        logger.info("실패!");
      }

    } catch (Exception e) {
      logger.info("클라이언트 요청 처리 중 오류 발생!");

      StringWriter out2 = new StringWriter();
      e.printStackTrace(new PrintWriter(out2));
      logger.debug(out2.toString());

      response.setStatusCode(HttpStatus.SC_OK);
      StringEntity entity = new StringEntity(
          "<html><body><h1>요청 처리 중 오류 발생!</h1></body></html>",
          ContentType.create("text/html", "UTF-8"));
      response.setEntity(entity);

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










