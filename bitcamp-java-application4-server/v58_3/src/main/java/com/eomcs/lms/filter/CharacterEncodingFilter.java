package com.eomcs.lms.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

// 역할: 
// => 서블릿을 실행하기 전에,
//    POST로 전달 받은 데이터의 인코딩을 UTF-8로 설정한다.
//
public class CharacterEncodingFilter implements Filter {
  
  String charset = "UTF-8";
  FilterConfig config;
  
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.config = filterConfig;
    String value = filterConfig.getInitParameter("encoding");
    if (value != null) {
      charset = value;
    }
  }
  
  @Override
  public void doFilter(
      ServletRequest request, 
      ServletResponse response, 
      FilterChain chain)
      throws IOException, ServletException {
    
    // 다음 필터나 서블릿을 실행하기 전에 클라이언트가 POST로 보낸 데이터의 인코딩을 
    // UTF-8로 설정한다.
    request.setCharacterEncoding(charset);
    
    // 다음 필터 또는 서블릿을 실행한다.
    chain.doFilter(request, response);
  }
}








