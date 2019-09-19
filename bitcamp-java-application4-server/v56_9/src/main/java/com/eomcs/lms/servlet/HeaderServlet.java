package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.eomcs.lms.domain.Member;

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
    out.println("<nav id='header' class='navbar navbar-expand-lg navbar-light bg-light'>");
    out.println("<a class='navbar-brand' href='#'>\n"
        + "<img src='/images/logo.png' class='d-inline-block align-top'>\n"
        + "수업관리시스템</a>");
    out.println("<button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNav\" aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" + 
        "    <span class=\"navbar-toggler-icon\"></span>\n" + 
        "  </button>");
    out.println("<div class='collapse navbar-collapse' id='navbarSupportedContent'>");
    out.println("<ul class='navbar-nav'>");
    out.println("  <li class='nav-item active'>");
    out.println("    <a class='nav-link' href='/board/list'>게시판</a>");
    out.println("  </li>");
    out.println("  <li class='nav-item active'>");
    out.println("    <a class='nav-link' href='/lesson/list'>수업관리</a>");
    out.println("  </li>");
    out.println("  <li class='nav-item active'>");
    out.println("    <a class='nav-link' href='/member/list'>회원관리</a>");
    out.println("  </li>");
    out.println("  <li class='nav-item active'>");
    out.println("    <a class='nav-link' href='/photoboard/list'>사진게시판</a>");
    out.println("  </li>");
    out.println("</ul>");
    out.println("</div>");
    
    out.println("<div>");
    HttpSession session = request.getSession();
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      out.println("  <a href='/auth/login' class='btn btn-outline-dark btn-sm'>로그인</a>");
    } else {
      out.printf("<a href='/member/detail?no=%d'>%s</a>", 
          loginUser.getNo(),
          loginUser.getName());
      out.println("<a href='/auth/logout' class='btn btn-outline-dark btn-sm'>로그아웃</a>");
    }
    out.println("</div>");
    
    out.println("</nav>");
  }
}










