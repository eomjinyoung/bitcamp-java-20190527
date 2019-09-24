package com.eomcs.lms.controller;

import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

@Controller
public class AuthController {

  @Resource
  private MemberDao memberDao;

  @RequestMapping("/auth/login")
  public String login(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    if (request.getMethod().equalsIgnoreCase("GET")) {
      return "/jsp/auth/form.jsp";
    }

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
    return "redirect:../board/list";
  }
  
  @RequestMapping("/auth/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    
    request.getSession().invalidate();
    return "redirect:login";
  }

}
