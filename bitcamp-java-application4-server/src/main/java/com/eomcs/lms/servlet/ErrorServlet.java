package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// 역할:
// => 다른 서블릿에서 발생한 오류 내용을 출력한다.
//
@WebServlet("/error")
public class ErrorServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static final Logger logger = 
      LogManager.getLogger(ErrorServlet.class);
  
  @Override
  protected void service(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>실행 오류</title></head>");
    out.println("<body><h1>실행 오류!</h1>");
    out.printf("<p>%s</p>\n", request.getAttribute("message"));
    out.println("</body></html>");
    
    String url = (String) request.getAttribute("refresh");
    if (url != null) {
      response.setHeader("Refresh", "1;url=" + url);
    }

    Exception e = (Exception) request.getAttribute("error");
    if (e != null) {
      // 왜 오류가 발생했는지 자세한 사항은 로그로 남긴다.
      StringWriter strOut = new StringWriter();
      e.printStackTrace(new PrintWriter(strOut));
      logger.error(strOut.toString());
    }
  }
}






