package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class HelloCommand {
  
  @RequestMapping("/hello") // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void execute(BufferedReader in, PrintWriter out) {
    out.println("<html><body><h1>안녕하세요!</h1><p>오호라...인사하기</p></body></html>");
  }

}
