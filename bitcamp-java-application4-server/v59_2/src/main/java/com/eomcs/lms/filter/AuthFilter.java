package com.eomcs.lms.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 역할:
// => 로그인 사용자만 등록, 변경, 삭제를 수행할 수 있게 한다.
//
public class AuthFilter implements Filter {

  String[] path;
  
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // web.xml에 설정된 초기화 파라미터 값을 가져온다.
    path = filterConfig.getInitParameter("path").split(",");
  }
  
  @Override
  public void doFilter(
      ServletRequest request, 
      ServletResponse response, 
      FilterChain chain)
      throws IOException, ServletException {
    
    HttpServletRequest httpReq = (HttpServletRequest) request;
    HttpServletResponse httpResp = (HttpServletResponse) response;
    
    String servletPath = httpReq.getServletPath();
    
    for (String p : path) {
      // web.xml에 지정된 경로라면, 로그인 여부를 검사한다.
      if (servletPath.endsWith(p)) {
        
        if (httpReq.getSession().getAttribute("loginUser") == null) {
          // 로그인 하지 않았다면 로그인 폼으로 보낸다.
          httpResp.sendRedirect("/auth/login");
          return;
          
        } else {
          // 로그인 했다면 반복문을 멈추고, 다음 목적지로 보낸다.
          break;
        }
      }
    }
      
    // 로그인 했다면 원래의 목적지로 보낸다.
    chain.doFilter(request, response);
  }
}








