package com.eomcs.util;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Date;

// 클라이언트에서 입력 받는 기능을 수행
public class Input {
  
  static final String PARAMETER_MESSAGE = "!{}!";
  
  private static void requestParameterWithTitle(
      BufferedReader in, PrintStream out, String title) throws Exception {
    out.println(title);
    out.println(PARAMETER_MESSAGE);
    out.flush();
  }
  
  public static int getIntValue(BufferedReader in, PrintStream out, String title) 
      throws Exception {
    requestParameterWithTitle(in, out, title);
    return Integer.parseInt(in.readLine());
  }
  
  public static Date getDateValue(BufferedReader in, PrintStream out, String title) 
      throws Exception {
    requestParameterWithTitle(in, out, title);
    return Date.valueOf(in.readLine());
  }
  
  public static String getStringValue(BufferedReader in, PrintStream out, String title) 
      throws Exception {
    requestParameterWithTitle(in, out, title);
    return in.readLine();
  }
}
