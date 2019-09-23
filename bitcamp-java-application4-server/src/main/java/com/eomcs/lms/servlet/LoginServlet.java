package com.eomcs.lms.servlet;

import java.io.IOException;
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
    request.setAttribute("viewUrl", "/jsp/auth/form.jsp");
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    
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
        throw new Exception("이메일 또는 암호가 맞지 않습니다!");
      } 
      
      HttpSession session = request.getSession();
      session.setAttribute("loginUser", member);
      request.setAttribute("viewUrl", "redirect:../board/list");
      
    } catch (Exception e) {
      request.setAttribute("error", e);
      request.setAttribute("refresh", "login");
    }
  }

}
