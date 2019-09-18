package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 역할:
// => 화면 하단에 제작자 또는 사이트의 소유주 정보를 출력한다.
//
@WebServlet("/footer")
public class FooterServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void service(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 인클루딩 되는 서블릿에서는 setContentType()을 호출해봐야 소용없다.
    // 이 서블릿을 요청하는 쪽에서 처리해야 한다.
    //response.setContentType("text/html;charset=UTF-8");
    
    PrintWriter out = response.getWriter();
    out.println("<div id='footer'>");
    out.println("  &copy; 2019");
    out.println("  <span>비트캠프, 자바130기</span>");
    out.println("</div>");
  }
}










