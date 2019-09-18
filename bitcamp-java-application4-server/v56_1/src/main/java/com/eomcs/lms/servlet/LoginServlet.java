package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>로그인 폼</title></head>");
    out.println("<body><h1>로그인 폼</h1>");
    out.println("<form action='/auth/login' method='post'>");
    out.println("이메일: <input type='text' name='email'><br>");
    out.println("암호: <input type='text' name='password'><br>");
    out.println("<button>로그인</button>");
    out.println("</form>");
    out.println("</body></html>");
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>로그인 결과</title></head>");
    out.println("<body><h1>로그인</h1>");
    try {
      HashMap<String,Object> params = new HashMap<>();
      params.put("email", request.getParameter("email"));
      params.put("password", request.getParameter("password"));
      
      Member member = memberDao.findByEmailPassword(params);
      
      if (member == null) {
        out.println("<p>이메일 또는 암호가 맞지 않습니다!</p>");
      } else {
        out.printf("<p>%s 님 환영합니다.</p>\n", member.getName());
      }
      
    } catch (Exception e) {
      out.println("<p>로그인 처리에 실패했습니다!</p>");
      throw new RuntimeException(e);
      
    } finally {
      out.println("</body></html>");
    }
  }

}
