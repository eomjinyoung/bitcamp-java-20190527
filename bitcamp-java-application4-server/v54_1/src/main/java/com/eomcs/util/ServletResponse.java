package com.eomcs.util;

import java.io.PrintWriter;
import java.io.StringWriter;

// 응답 도구 및 응답 내용을 관리하는 객체
public class ServletResponse {
  
  StringWriter out = new StringWriter();
  
  public PrintWriter getWriter() {
    return new PrintWriter(out);
  }
  
  public String getResponseEntity() {
    return out.toString();
  }
}
