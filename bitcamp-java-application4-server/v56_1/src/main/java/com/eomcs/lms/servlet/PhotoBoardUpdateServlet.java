package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;

@WebServlet("/photoboard/update")
public class PhotoBoardUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  // 이 클래스에서 로그를 출력할 일이 있다면 다음과 같이 로거를 만들어 사용하라!
  /*
  private static final Logger logger = 
      LogManager.getLogger(PhotoBoardAddServlet.class);
  */
  
  private PhotoBoardDao photoBoardDao;
  private PhotoFileDao photoFileDao;
  
  @Override
  public void init() throws ServletException {
    ApplicationContext appCtx = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
    photoBoardDao = appCtx.getBean(PhotoBoardDao.class);
    photoFileDao = appCtx.getBean(PhotoFileDao.class);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>사진게시물 변경</title>"
        + "<meta http-equiv='Refresh' content='1;url=/photoboard/list'>"
        + "</head>");
    out.println("<body><h1>사진게시물 변경</h1>");
    try {
      PhotoBoard photoBoard = new PhotoBoard();
      photoBoard.setNo(Integer.parseInt(request.getParameter("no")));
      photoBoard.setTitle(request.getParameter("title"));

      photoBoardDao.update(photoBoard);
      photoFileDao.deleteAll(photoBoard.getNo());

      int count = 0;
      for (int i = 1; i <= 6; i++) {
        String filepath = request.getParameter("filePath" + i);
        if (filepath.length() == 0) {
          continue;
        }
        PhotoFile photoFile = new PhotoFile();
        photoFile.setFilePath(filepath);
        photoFile.setBoardNo(photoBoard.getNo());
        photoFileDao.insert(photoFile);
        count++;
      }
      
      if (count == 0) {
        out.println("<p>최소 한 개의 사진 파일을 등록해야 합니다.</p>");
        throw new Exception("사진 파일 없음!");
      }
      
      out.println("<p>변경하였습니다.</p>");
      
    } catch (Exception e) {
      out.println("<p>데이터 변경에 실패했습니다!</p>");
      throw new RuntimeException(e);
    
    } finally {
      out.println("</body></html>");
    }
  }

}
