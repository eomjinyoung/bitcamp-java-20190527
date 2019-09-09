package com.eomcs.lms.handler;

import java.io.PrintWriter;
import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;
import com.eomcs.util.ServletRequest;
import com.eomcs.util.ServletResponse;

@Component
public class LoginCommand {
  
  private MemberDao memberDao;
  
  public LoginCommand(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @RequestMapping("/auth/form")
  public void form(ServletRequest request, ServletResponse response) {
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>로그인 폼</title></head>");
    out.println("<body><h1>로그인 폼</h1>");
    out.println("<form action='/auth/login'>");
    out.println("이메일: <input type='text' name='email'><br>");
    out.println("암호: <input type='text' name='password'><br>");
    out.println("<button>로그인</button>");
    out.println("</form>");
    out.println("</body></html>");
  }
  
  @RequestMapping("/auth/login") 
  public void execute(ServletRequest request, ServletResponse response) {
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
