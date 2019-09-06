package com.eomcs.lms;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import com.eomcs.util.RequestMappingHandlerMapping;

public class CommandHttpRequestHandler implements HttpRequestHandler {

  RequestMappingHandlerMapping handlerMapping;
  
  public CommandHttpRequestHandler(RequestMappingHandlerMapping handlerMapping) {
    this.handlerMapping = handlerMapping;
  }
  
  @Override
  public void handle(HttpRequest request, HttpResponse response, HttpContext context)
      throws HttpException, IOException {
    
    
  }

}







