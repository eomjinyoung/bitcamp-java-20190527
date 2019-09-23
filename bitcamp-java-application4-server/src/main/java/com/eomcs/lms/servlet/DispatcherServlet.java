package com.eomcs.lms.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private static final Logger log = LogManager.getLogger(DispatcherServlet.class);
  
  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    
    String servletPath = request.getServletPath();
    String pathInfo = request.getPathInfo();
    log.debug(String.format("요청 => servletPath: %s, pathInfo: %s", 
        servletPath, pathInfo));
    
    // 클라이언트가 요청한 서블릿(page controller)에게 실행을 위임한다.
    RequestDispatcher rd = request.getRequestDispatcher(pathInfo);
    rd.include(request, response);
    
    Exception error = (Exception) request.getAttribute("error");
    if (error != null) {
      request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
      return;
    }
    
    // 페이지 컨트롤러 작업을 수행한 후 JSP를 실행한다.
    String contentType = (String) request.getAttribute("contentType");
    if (contentType != null) {
      response.setContentType(contentType);
    } else {
      response.setContentType("text/html;charset=UTF-8");
    }
    
    String viewUrl = (String) request.getAttribute("viewUrl");
    if (viewUrl != null) {
      if (viewUrl.startsWith("redirect:")) {
        response.sendRedirect(viewUrl.substring(9)); // "redirect:list"
      } else {
        rd = request.getRequestDispatcher(viewUrl);
        rd.include(request, response);
      }
    }
    
  }
}







