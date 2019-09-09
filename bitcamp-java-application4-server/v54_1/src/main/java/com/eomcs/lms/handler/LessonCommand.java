package com.eomcs.lms.handler;

import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.util.ServletRequest;
import com.eomcs.util.ServletResponse;

@Component
public class LessonCommand {

  private LessonDao lessonDao;

  public LessonCommand(LessonDao lessonDao) {
    this.lessonDao = lessonDao;
  }

  @RequestMapping("/lesson/form")
  public void form(ServletRequest request, ServletResponse response) {
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>수업 등록폼</title></head>");
    out.println("<body><h1>수업 등록폼</h1>");
    out.println("<form action='/lesson/add'>");
    out.println("수업명: <input type='text' name='title'><br>");
    out.println("설명 : <textarea name='contents' rows='5' cols='50'></textarea><br>");
    out.println("시작일: <input type='text' name='startDate'><br>");
    out.println("종료일: <input type='text' name='endDate'><br>");
    out.println("총 수업시간: <input type='text' name='totalHours'><br>");
    out.println("일 수업시간: <input type='text' name='dayHours'><br>");
    out.println("<button>등록</button>");
    out.println("</form>");
    out.println("</body></html>");
  }
  
  @RequestMapping("/lesson/add") 
  public void add(ServletRequest request, ServletResponse response) {
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>수업 등록</title>"
        + "<meta http-equiv='Refresh' content='1;url=/lesson/list'>"
        + "</head>");
    out.println("<body><h1>수업 등록</h1>");
    try {
      Lesson lesson = new Lesson();
      
      lesson.setTitle(request.getParameter("title"));
      lesson.setContents(request.getParameter("contents"));
      lesson.setStartDate(Date.valueOf(request.getParameter("startDate")));
      lesson.setEndDate(Date.valueOf(request.getParameter("endDate")));
      lesson.setTotalHours(Integer.parseInt(request.getParameter("totalHours")));
      lesson.setDayHours(Integer.parseInt(request.getParameter("dayHours")));
      
      lessonDao.insert(lesson);
      out.println("<p>저장하였습니다.</p>");
      
    } catch (Exception e) {
      out.println("<p>데이터 저장에 실패했습니다!</p>");
      throw new RuntimeException(e);
      
    } finally {
      out.println("</body></html>");
    }
  }
  
  @RequestMapping("/lesson/delete") 
  public void delete(ServletRequest request, ServletResponse response) {
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>수업 삭제</title>"
        + "<meta http-equiv='Refresh' content='1;url=/lesson/list'>"
        + "</head>");
    out.println("<body><h1>수업 삭제</h1>");
    
    try {
      int no = Integer.parseInt(request.getParameter("no"));
      
      if (lessonDao.delete(no) > 0) {
        out.println("<p>데이터를 삭제하였습니다.</p>");
      } else {
        out.println("<p>해당 데이터가 없습니다.</p>");
      }
      
    } catch (Exception e) {
      out.println("<p>데이터 삭제에 실패했습니다!</p>");
      throw new RuntimeException(e);
      
    } finally {
      out.println("</body></html>");
    }
  }

  @RequestMapping("/lesson/detail") 
  public void detail(ServletRequest request, ServletResponse response) {
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>수업 상세</title></head>");
    out.println("<body><h1>수업 상세</h1>");
    
    try {
      int no = Integer.parseInt(request.getParameter("no"));
     
      Lesson lesson = lessonDao.findBy(no);
      if (lesson == null) {
        out.println("<p>해당 번호의 데이터가 없습니다!</p>");

      } else {
        out.println("<form action='/lesson/update'>");
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
      out.println("</body></html>");
    }
  }
  
  @RequestMapping("/lesson/list") 
  public void list(ServletRequest request, ServletResponse response) {
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>수업 목록</title>"
        + "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>"
        + "</head>");
    out.println("<body><h1>수업 목록</h1>");
    out.println("<a href='/lesson/form'>새 수업</a><br>");
    try {
      out.println("<table class='table table-hover'>");
      out.println("<tr><th>번호</th><th>수업</th><th>기간</th><th>총수업시간</th></tr>");
      List<Lesson> lessons = lessonDao.findAll();
      for (Lesson lesson : lessons) {
        out.printf("<tr>"
            + "<td>%d</td>"
            + "<td><a href='/lesson/detail?no=%d'>%s</a></td>"
            + "<td>%s ~ %s</td>"
            + "<td>%d</td></tr>\n", 
            lesson.getNo(),
            lesson.getNo(),
            lesson.getTitle(), 
            lesson.getStartDate(), 
            lesson.getEndDate(), 
            lesson.getTotalHours());
      }
      out.println("</table>");
      
    } catch (Exception e) {
      out.println("<p>데이터 목록 조회에 실패했습니다!</p>");
      throw new RuntimeException(e);
      
    } finally {
      out.println("</body></html>");
    }
  }
  
  @RequestMapping("/lesson/update") 
  public void update(ServletRequest request, ServletResponse response) {
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>수업 변경</title>"
        + "<meta http-equiv='Refresh' content='1;url=/lesson/list'>"
        + "</head>");
    out.println("<body><h1>수업 변경</h1>");
    try {
      Lesson lesson = new Lesson();
      lesson.setNo(Integer.parseInt(request.getParameter("no")));
      lesson.setTitle(request.getParameter("title"));
      lesson.setContents(request.getParameter("contents"));
      lesson.setStartDate(Date.valueOf(request.getParameter("startDate")));
      lesson.setEndDate(Date.valueOf(request.getParameter("endDate")));
      lesson.setTotalHours(Integer.parseInt(request.getParameter("totalHours")));
      lesson.setDayHours(Integer.parseInt(request.getParameter("dayHours")));
      
      lessonDao.update(lesson);
      out.println("<p>변경 했습니다</p>");
      
    } catch (Exception e) {
      out.println("<p>데이터 변경에 실패했습니다!</p>");
      throw new RuntimeException(e);
      
    } finally {
      out.println("</body></html>");
    }
  }
}












