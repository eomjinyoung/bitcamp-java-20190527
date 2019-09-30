package com.eomcs.lms.web;

import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

@Controller
@RequestMapping("/auth")
public class AuthController {

  @Resource
  private MemberDao memberDao;

  @GetMapping("form")
  public void form() {
  }
  
  @PostMapping("login")
  public String login(
      HttpServletResponse response,
      HttpSession session,
      String email,
      String password) 
      throws Exception {

    HashMap<String,Object> params = new HashMap<>();
    params.put("email", email);
    params.put("password", password);

    // 응답할 때 클라이언트가 입력한 이메일을 쿠키로 보낸다.
    Cookie cookie = new Cookie("email", email);
    cookie.setMaxAge(60 * 60 * 24 * 15);
    response.addCookie(cookie);

    Member member = memberDao.findByEmailPassword(params);
    if (member == null) {
      throw new Exception("이메일 또는 암호가 맞지 않습니다!");
    } 

    session.setAttribute("loginUser", member);
    return "redirect:../board/list";
  }
  
  @GetMapping("logout")
  public String logout(HttpSession session) 
      throws Exception {
    session.invalidate();
    return "redirect:form";
  }

}
