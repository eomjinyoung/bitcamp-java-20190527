package com.eomcs.lms.servlet;

import java.io.IOException;
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
@WebServlet("/photoboard/update")
public class PhotoBoardUpdateServlet extends HttpServlet {
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
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    try {
      PhotoBoard photoBoard = new PhotoBoard();
      photoBoard.setNo(Integer.parseInt(request.getParameter("no")));
      photoBoard.setTitle(request.getParameter("title"));

      photoBoardDao.update(photoBoard);
      photoFileDao.deleteAll(photoBoard.getNo());

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
      request.setAttribute("message", "데이터 변경에 실패했습니다!");
      request.setAttribute("refresh", "/photoboard/list");
      request.setAttribute("error", e);
      request.getRequestDispatcher("/error").forward(request, response);
    }
  }

}
