package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

@WebServlet("/lesson/detail")
public class LessonDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private LessonDao lessonDao;

  @Override
  public void init() throws ServletException {
    ApplicationContext appCtx = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
    lessonDao = appCtx.getBean(LessonDao.class);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>수업 상세</title>"
        + "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>"
        + "<link rel='stylesheet' href='/css/common.css'>"
        + "</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);
    
    out.println("<div id='content'>");
    out.println("<h1>수업 상세</h1>");
    
    try {
      int no = Integer.parseInt(request.getParameter("no"));
     
      Lesson lesson = lessonDao.findBy(no);
      if (lesson == null) {
        out.println("<p>해당 번호의 데이터가 없습니다!</p>");

      } else {
        out.println("<form action='/lesson/update' method='post'>");
        out.printf("번호: <input type='text' name='no' value='%d' readonly><br>\n",
            lesson.getNo());
        out.printf("수업명: <input type='text' name='title' value='%s'><br>\n",
            lesson.getTitle());
        out.printf("설명: <textarea name='contents' rows='5' cols='50'>%s</textarea><br>\n",
            lesson.getContents());
        out.printf("시작일: <input type='text' name='startDate' value='%s'><br>\n",
            lesson.getStartDate());
        out.printf("종료일: <input type='text' name='endDate' value='%s'><br>\n",
            lesson.getEndDate());
        out.printf("총 수업시간: <input type='text' name='totalHours' value='%d'><br>\n",
            lesson.getTotalHours());
        out.printf("일 수업시간: <input type='text' name='dayHours' value='%d'><br>\n",
            lesson.getDayHours());
        out.println("<button>변경</button>");
        out.printf("<a href='/lesson/delete?no=%d'>삭제</a>\n", lesson.getNo());
        out.println("</form>");
      } 
    } catch (Exception e) {
      out.println("<p>데이터 조회에 실패했습니다!</p>");
      throw new RuntimeException(e);
      
    } finally {
      out.println("</div>");
      request.getRequestDispatcher("/footer").include(request, response);
      out.println("</body></html>");
    }
  }
}












