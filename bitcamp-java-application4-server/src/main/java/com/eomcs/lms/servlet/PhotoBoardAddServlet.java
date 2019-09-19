package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/photoboard/add")
public class PhotoBoardAddServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  String uploadDir;
  private PhotoBoardDao photoBoardDao;
  private PhotoFileDao photoFileDao;
  
  @Override
  public void init() throws ServletException {
    ApplicationContext appCtx = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
    photoBoardDao = appCtx.getBean(PhotoBoardDao.class);
    photoFileDao = appCtx.getBean(PhotoFileDao.class);
    uploadDir = getServletContext().getRealPath("/upload/photoboard");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>사진게시물 등록폼</title>"
        + "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>"
        + "<link rel='stylesheet' href='/css/common.css'>"
        + "</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);
    
    out.println("<div id='content'>");
    out.println("<h1>사진게시물 등록폼</h1>");
    out.println("<form action='/photoboard/add' method='post' enctype='multipart/form-data'>");
    out.println("제목: <input type='text' name='title'><br>");
    out.println("수업: <input type='text' name='lessonNo'><br>");
    out.println("사진1: <input type='file' name='filePath'><br>");
    out.println("사진2: <input type='file' name='filePath'><br>");
    out.println("사진3: <input type='file' name='filePath'><br>");
    out.println("사진4: <input type='file' name='filePath'><br>");
    out.println("사진5: <input type='file' name='filePath'><br>");
    out.println("사진6: <input type='file' name='filePath'><br>");
    out.println("<button>등록</button>");
    out.println("</form>");
    out.println("</div>");
    request.getRequestDispatcher("/footer").include(request, response);
    out.println("</body></html>");
  }
  
 
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    try {
      PhotoBoard photoBoard = new PhotoBoard();
      photoBoard.setTitle(request.getParameter("title"));
      photoBoard.setLessonNo(Integer.parseInt(request.getParameter("lessonNo")));
      
      photoBoardDao.insert(photoBoard);
      
      int count = 0;
      Collection<Part> parts = request.getParts();
      for (Part part : parts) {
        if (!part.getName().equals("filePath") || part.getSize() == 0) {
          continue;
        }
        // 클라이언트가 보낸 파일을 디스크에 저장한다.
        String filename = UUID.randomUUID().toString();
        part.write(uploadDir + "/" + filename);
        
        // 저장한 파일명을 DB에 입력한다.
        PhotoFile photoFile = new PhotoFile();
        photoFile.setFilePath(filename);
        photoFile.setBoardNo(photoBoard.getNo());
        photoFileDao.insert(photoFile);
        count++;
      }
      
      if (count == 0) {
        throw new Exception("사진 파일 없음!");
      }
      
      response.sendRedirect("/photoboard/list");
      
    } catch (Exception e) {
      request.setAttribute("message", "데이터 저장에 실패했습니다!");
      request.setAttribute("refresh", "/photoboard/list");
      request.setAttribute("error", e);
      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}
