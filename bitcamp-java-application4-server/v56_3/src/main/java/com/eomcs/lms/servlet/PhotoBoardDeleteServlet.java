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
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;

@WebServlet("/photoboard/delete")
public class PhotoBoardDeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private static final Logger logger = 
      LogManager.getLogger(PhotoBoardDeleteServlet.class);
  
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
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    try {
      int no = Integer.parseInt(request.getParameter("no"));
      
      if (photoBoardDao.findBy(no) == null) {
        throw new Exception("해당 데이터가 없습니다.");
      }
      
      photoFileDao.deleteAll(no);
      photoBoardDao.delete(no);
      response.sendRedirect("/photoboard/list");
      
    } catch (Exception e) {
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<html><head><title>사진게시물 삭제</title></head>");
      out.println("<body><h1>사진게시물 삭제</h1>");
      out.println("<p>데이터 삭제에 실패했습니다!</p>");
      out.println("</body></html>");
      response.setHeader("Refresh", "1;url=/photoboard/list");

      // 왜 오류가 발생했는지 자세한 사항은 로그로 남긴다.
      StringWriter strOut = new StringWriter();
      e.printStackTrace(new PrintWriter(strOut));
      logger.error(strOut.toString());
    }
  }
}
