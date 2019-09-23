package com.eomcs.lms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.controller.PageController;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private static final Logger log = LogManager.getLogger(DispatcherServlet.class);
  
  private ApplicationContext iocContainer;
  
  @Override
  public void init() throws ServletException {
    iocContainer = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
  }
  
  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    
    String servletPath = request.getServletPath();
    String pathInfo = request.getPathInfo();
    log.debug(String.format("요청 => servletPath: %s, pathInfo: %s", 
        servletPath, pathInfo));
    
    try {
      // 클라이언트 요청을 처리할 PageController를 구현한 객체를 찾는다.
      PageController pageController = (PageController) iocContainer.getBean(pathInfo);
      
      // 페이지 컨트롤러에게 실행을 위임한다.
      String viewUrl = pageController.execute(request, response);
      
      // 응답 콘텐트의 MIME 타입과 문자집합을 설정한다.
      String contentType = (String) request.getAttribute("contentType");
      if (contentType != null) {
        response.setContentType(contentType);
      } else {
        response.setContentType("text/html;charset=UTF-8");
      }
      
      // 페이지 컨트롤러 작업을 수행한 후 리턴 URL에 따라 JSP를 실행한다.
      if (viewUrl != null) {
        if (viewUrl.startsWith("redirect:")) {
          response.sendRedirect(viewUrl.substring(9)); // "redirect:list"
        } else {
          request.getRequestDispatcher(viewUrl).include(request, response);
        }
      }
      
    } catch (Exception e) {
      request.setAttribute("error", e);
      request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
    }
  }
}







