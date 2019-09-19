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
import com.eomcs.lms.domain.PhotoFile;

@WebServlet("/photoboard/detail")
public class PhotoBoardDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private PhotoBoardDao photoBoardDao;
  
  @Override
  public void init() throws ServletException {
    ApplicationContext appCtx = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
    photoBoardDao = appCtx.getBean(PhotoBoardDao.class);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>사진게시물 상세</title>"
        + "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>"
        + "<link rel='stylesheet' href='/css/common.css'>"
        + "</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);
    
    out.println("<div id='content'>");
    out.println("<h1>사진게시물 상세</h1>");
    
    try {
      int no = Integer.parseInt(request.getParameter("no"));
      
      PhotoBoard photoBoard = photoBoardDao.findWithFilesBy(no);
      if (photoBoard == null) {
        out.println("<p>해당 번호의 데이터가 없습니다!</p>");

      } else {
        photoBoardDao.increaseViewCount(no);
        
        out.println("<form action='/photoboard/update'"
            + " method='post' enctype='multipart/form-data'>");
        out.printf("번호: <input type='text' name='no' value='%d' readonly><br>\n",
            photoBoard.getNo());
        out.printf("제목: <input type='text' name='title' value='%s'><br>\n",
            photoBoard.getTitle());
        out.printf("수업: %d<br>\n",
            photoBoard.getLessonNo());
        out.printf("조회수: %d<br>\n",
            photoBoard.getViewCount());
        
        out.println("<p>");
        List<PhotoFile> files = photoBoard.getFiles();
        for (PhotoFile file : files) {
          if (file.getFilePath() == null)
            continue;
          out.printf("<img src='/upload/photoboard/%s' class='photo2'>", 
              file.getFilePath());
        }
        out.println("</p>");
        
        for (int i = 0; i < 6; i++) {
          out.println("사진: <input type='file' name='filePath'><br>");
        }
        
        out.println("<button>변경</button>");
        out.printf("<a href='/photoboard/delete?no=%d'>삭제</a>\n", 
            photoBoard.getNo());
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
