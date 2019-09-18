package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 역할:
// => 화면 상단에 로고와 프로젝트명을 출력한다.
// => 로그인 정보를 출력한다.
// => 로그인/로그아웃 버튼을 제공한다.
//
@WebServlet("/header")
public class HeaderServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void service(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 인클루딩 되는 서블릿에서는 setContentType()을 호출해봐야 소용없다.
    // 이 서블릿을 요청하는 쪽에서 처리해야 한다.
    //response.setContentType("text/html;charset=UTF-8");
    
    PrintWriter out = response.getWriter();
    out.println("<div id='header'>");
    out.println("  <img src='/images/logo.png'>");
    out.println("  <span>수업관리시스템</span>");
    out.println("  <a href='/auth/login'>로그인</a>");
    out.println("  <a href='/auth/logout'>로그아웃</a>");
    out.println("</div>");
  }
}










