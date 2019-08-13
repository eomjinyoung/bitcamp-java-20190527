package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;

public class HelloCommand implements Command {
  
  @Override
  public void execute(BufferedReader in, PrintStream out) {
    try {
      out.println("아무거나....");
      
    } catch (Exception e) {
      out.println("데이터 목록 조회에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }

}
