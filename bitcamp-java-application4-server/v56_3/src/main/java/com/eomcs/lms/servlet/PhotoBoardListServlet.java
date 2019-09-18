package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.domain.PhotoBoard;

@WebServlet("/photoboard/list")
public class PhotoBoardListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  // 이 클래스에서 로그를 출력할 일이 있다면 다음과 같이 로거를 만들어 사용하라!
  /*
  private static final Logger logger = 
      LogManager.getLogger(PhotoBoardAddServlet.class);
  */
  
  private PhotoBoardDao photoBoardDao;
  
  @Override
  public void init() throws ServletException {
    ApplicationContext appCtx = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
    photoBoardDao = appCtx.getBean(PhotoBoardDao.class);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>사진게시물 목록</title>"
        + "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>"
        + "</head>");
    out.println("<body><h1>사진게시물 목록</h1>");
    out.println("<a href='/photoboard/add'>새 사진게시물</a><br>");
    
    try {
      out.println("<table class='table table-hover'>");
      out.println("<tr><th>번호</th><th>제목</th><th>등록일</th><th>조회수</th><th>수업</th></tr>");
      List<PhotoBoard> photoBoards = photoBoardDao.findAll();
      for (PhotoBoard photoBoard : photoBoards) {
        out.printf("<tr>"
            + "<td>%d</td>"
            + "<td><a href='/photoboard/detail?no=%d'>%s</a></td>"
            + "<td>%s</td>"
            + "<td>%d</td>"
            + "<td>%d</td></tr>\n", 
            photoBoard.getNo(),
            photoBoard.getNo(),
            photoBoard.getTitle(), 
            photoBoard.getCreatedDate(), 
            photoBoard.getViewCount(),
            photoBoard.getLessonNo());
      }
      out.println("</table>");
      
    } catch (Exception e) {
      out.println("<p>데이터 목록 조회에 실패했습니다!</p>");
      throw new RuntimeException(e);
    
    } finally {
      out.println("</body></html>");
    }
  }
}
