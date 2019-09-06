package com.eomcs.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import com.eomcs.util.RequestMappingHandlerMapping;
import com.eomcs.util.RequestMappingHandlerMapping.RequestHandler;

public class CommandHttpRequestHandler implements HttpRequestHandler {

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

}







