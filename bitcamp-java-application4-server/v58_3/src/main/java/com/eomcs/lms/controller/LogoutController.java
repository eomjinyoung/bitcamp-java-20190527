package com.eomcs.lms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component("/auth/logout")
public class LogoutController {
  
  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    
    request.getSession().invalidate();
    return "redirect:login";
  }
}
