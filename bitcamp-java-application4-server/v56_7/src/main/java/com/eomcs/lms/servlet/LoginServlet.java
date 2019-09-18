package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private MemberDao memberDao;
  
  @Override
  public void init() throws ServletException {
    ApplicationContext appCtx = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
    memberDao = appCtx.getBean(MemberDao.class);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    
    // 클라이언트가 보낸 쿠키 중에서 이메일 값 꺼내기
    String email = "";
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("email")) {
          email = cookie.getValue();
          break;
        }
      }
    }
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>로그인 폼</title>"
        + "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>"
        + "<link rel='stylesheet' href='/css/common.css'>"
        + "</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);
    
    out.println("<div id='content'>");
    out.println("<h1>로그인 폼</h1>");
    out.println("<form action='/auth/login' method='post'>");
    out.printf("이메일: <input type='text' name='email' value='%s'><br>\n", email);
    out.println("암호: <input type='text' name='password'><br>");
    out.println("<button>로그인</button>");
    out.println("</form>");
    out.println("</div>");
    request.getRequestDispatcher("/footer").include(request, response);
    out.println("</body></html>");
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>로그인 결과</title>"
        + "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>"
        + "<link rel='stylesheet' href='/css/common.css'>"
        + "</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);
    
    out.println("<div id='content'>");
    out.println("<h1>로그인</h1>");
    try {
      HashMap<String,Object> params = new HashMap<>();
      params.put("email", request.getParameter("email"));
      params.put("password", request.getParameter("password"));
      
      // 응답할 때 클라이언트가 입력한 이메일을 쿠키로 보낸다.
      Cookie cookie = new Cookie("email", request.getParameter("email"));
      cookie.setMaxAge(60 * 60 * 24 * 15);
      response.addCookie(cookie);
      
      Member member = memberDao.findByEmailPassword(params);
      
      if (member == null) {
        out.println("<p>이메일 또는 암호가 맞지 않습니다!</p>");
        response.setHeader("Refresh", "2;url=/auth/login");
        
      } else {
        out.printf("<p>%s 님 환영합니다.</p>\n", member.getName());
       
        // 로그인 사용자의 정보를 HttpSession 보관소에 저장한다.
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", member);
        response.setHeader("Refresh", "2;url=/board/list");
      }
      
    } catch (Exception e) {
      request.setAttribute("message", "로그인 처리에 실패했습니다!");
      request.setAttribute("refresh", "/auth/login");
      request.setAttribute("error", e);
      request.getRequestDispatcher("/error").forward(request, response);
      
    } finally {
      out.println("</div>");
      request.getRequestDispatcher("/footer").include(request, response);
      out.println("</body></html>");
    }
  }

}
