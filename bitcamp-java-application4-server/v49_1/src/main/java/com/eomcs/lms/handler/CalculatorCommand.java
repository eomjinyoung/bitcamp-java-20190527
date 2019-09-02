package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import com.eomcs.util.Component;
import com.eomcs.util.Input;
import com.eomcs.util.RequestMapping;

@Component
public class CalculatorCommand {
  
  @RequestMapping("/calc/plus")
  public void plus(BufferedReader in, PrintStream out) {
    try {
      int a = Input.getIntValue(in, out, "값1? ");
      int b = Input.getIntValue(in, out, "값2? ");
      
      out.printf("%d + %d = %d\n", a, b, (a + b));
      
    } catch (Exception e) {
      out.println("계산 값이 옳지 않습니다.");
      e.printStackTrace();
    }
  }
  
  @RequestMapping("/calc/minus")
  public void minus(BufferedReader in, PrintStream out) {
    try {
      int a = Input.getIntValue(in, out, "값1? ");
      int b = Input.getIntValue(in, out, "값2? ");
      
      out.printf("%d - %d = %d\n", a, b, (a - b));
      
    } catch (Exception e) {
      out.println("계산 값이 옳지 않습니다.");
      e.printStackTrace();
    }
  }
  
  
  
}

// 실습 과제:
// => 다음과 같이 실행하도록 위 클래스를 완성하라!
//
/*
명령> /calc/plus
값1? 100
값2? 200
100 + 200 = 300

명령> /calc/minus
값1? 100
값2? 200
100 - 200 = -100

 */