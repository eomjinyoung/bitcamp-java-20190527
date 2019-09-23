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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/photoboard/add")
public class PhotoBoardAddServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  String uploadDir;
  private PlatformTransactionManager txManager;
  private PhotoBoardDao photoBoardDao;
  private PhotoFileDao photoFileDao;
  
  @Override
  public void init() throws ServletException {
    ApplicationContext appCtx = 
        (ApplicationContext) getServletContext().getAttribute("iocContainer");
    txManager = appCtx.getBean(PlatformTransactionManager.class);
    photoBoardDao = appCtx.getBean(PhotoBoardDao.class);
    photoFileDao = appCtx.getBean(PhotoFileDao.class);
    uploadDir = getServletContext().getRealPath("/upload/photoboard");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    request.setAttribute("viewUrl", "/jsp/photoboard/form.jsp");
  }
 
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {
    
    // 트랜잭션 동작을 정의한다.
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    
    // 정의된 트랜잭션 동작에 따라 작업을 수행할 트랜잭션 객체를 준비한다. 
    TransactionStatus status = txManager.getTransaction(def);
    
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
      
      txManager.commit(status);
      
      request.setAttribute("viewUrl", "redirect:list");
      
    } catch (Exception e) { 
      
      txManager.rollback(status);
      
      request.setAttribute("error", e);
      request.setAttribute("refresh", "list");
    }
  }
}
