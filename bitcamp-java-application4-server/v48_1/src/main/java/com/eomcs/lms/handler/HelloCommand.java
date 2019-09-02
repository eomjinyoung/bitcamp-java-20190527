package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import com.eomcs.util.Component;
import com.eomcs.util.RequestMapping;

@Component("/hello")
public class HelloCommand {
  
  @RequestMapping // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void execute(BufferedReader in, PrintStream out) {
    out.println("안녕하세요!");
  }

}
