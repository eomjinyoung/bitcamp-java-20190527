package com.eomcs.lms.servlet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// 클라이언트로부터 요청이 들어오면 ServerApp에서 해당 요청을 처리하기 위해 
// 담당자를 호출하는 규칙
public interface Servlet {
  void service(
      String command, 
      ObjectInputStream in, 
      ObjectOutputStream out) throws Exception;
}
