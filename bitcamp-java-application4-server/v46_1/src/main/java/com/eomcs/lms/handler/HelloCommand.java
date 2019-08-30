package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;

public class HelloCommand implements Command {
  
  public String getCommandName() {
    return "/hello";
  }
  
  @Override
  public void execute(BufferedReader in, PrintStream out) {
    out.println("안녕하세요!");
  }

}
