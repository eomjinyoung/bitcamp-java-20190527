package com.eomcs.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.eomcs.util.RequestMappingHandlerMapping;
import com.eomcs.util.RequestMappingHandlerMapping.RequestHandler;

public class CommandHttpRequestHandler implements HttpRequestHandler {

  private static final Logger logger = 
      LogManager.getLogger(CommandHttpRequestHandler.class);
  
  RequestMappingHandlerMapping handlerMapping;
  
  public CommandHttpRequestHandler(RequestMappingHandlerMapping handlerMapping) {
    this.handlerMapping = handlerMapping;
  }
  
  @Override
  public void handle(HttpRequest request, HttpResponse response, HttpContext context)
      throws HttpException, IOException {
    
    // 클라이언트가 요청한 방식을 알아 낸다.
    String method = request.getRequestLine().getMethod().toUpperCase(Locale.ROOT);
    if (!method.equals("GET")) { // GET 요청이 아니라면 오류 내용을 응답한다.
      throw new MethodNotSupportedException(method + " method not supported");
    }
    
    String command = request.getRequestLine().getUri().split("\\?")[0];
    logger.info(command);
    
    try {
      RequestHandler requestHandler = 
          handlerMapping.getRequestHandler(command);

      if (requestHandler != null) {
        // 클라이언트 요청 처리
        StringWriter out = new StringWriter();
        requestHandler.method.invoke(requestHandler.bean, 
            null, new PrintWriter(out));
        
        // 클라이언트에게 응답
        response.setStatusCode(HttpStatus.SC_OK);
        StringEntity entity = new StringEntity(
            out.toString(),
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

}







