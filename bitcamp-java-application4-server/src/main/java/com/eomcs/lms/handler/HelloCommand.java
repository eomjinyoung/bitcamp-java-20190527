package com.eomcs.lms.handler;

import java.io.PrintWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.util.ServletRequest;
import com.eomcs.util.ServletResponse;

@Component
public class HelloCommand {
  
  @RequestMapping("/hello") // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void execute(ServletRequest request, ServletResponse response) {
    PrintWriter out = response.getWriter();
    out.println("<html><body><h1>안녕하세요!</h1>");
    out.printf("v1 => %s <br>\n", request.getParameter("v1"));
    out.printf("v2 => %s <br>\n", request.getParameter("v2"));
    out.printf("v3 => %s <br>\n", request.getParameter("v3"));
    out.println("</body></html>");
  }

}
