package com.eomcs.lms.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;

@Controller
public class PhotoBoardController {
  
  @Resource private PlatformTransactionManager txManager;
  @Resource private PhotoBoardDao photoBoardDao;
  @Resource private PhotoFileDao photoFileDao;
  
  @RequestMapping("/photoboard/form")
  public String form() {
    return "/jsp/photoboard/form.jsp";
  }
  
  @RequestMapping("/photoboard/add")
  public String add(
      HttpServletRequest request, 
      PhotoBoard photoBoard,
      Part[] filePath) throws Exception {
    
    String uploadDir = request.getServletContext().getRealPath("/upload/photoboard");
    // 트랜잭션 동작을 정의한다.
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    
    // 정의된 트랜잭션 동작에 따라 작업을 수행할 트랜잭션 객체를 준비한다. 
    TransactionStatus status = txManager.getTransaction(def);
    
    try {
      photoBoardDao.insert(photoBoard);
      
      int count = 0;
      for (Part part : filePath) {
        if (part.getSize() == 0)
          continue;
        
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
      return "redirect:list";
      
    } catch (Exception e) { 
      txManager.rollback(status);
      throw e;
    }
  }
  
  @RequestMapping("/photoboard/delete")
  public String delete(int no) 
      throws Exception {
    
    // 트랜잭션 동작을 정의한다.
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    
    // 정의된 트랜잭션 동작에 따라 작업을 수행할 트랜잭션 객체를 준비한다. 
    TransactionStatus status = txManager.getTransaction(def);
    
    try {
      if (photoBoardDao.findBy(no) == null) {
        throw new Exception("해당 데이터가 없습니다.");
      }
      
      photoFileDao.deleteAll(no);
      photoBoardDao.delete(no);
      
      txManager.commit(status);
      return "redirect:list";
      
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }
  
  @RequestMapping("/photoboard/detail")
  public String detail(Map<String,Object> model, int no) 
      throws Exception {

    PhotoBoard photoBoard = photoBoardDao.findWithFilesBy(no);
    if (photoBoard == null) {
      throw new Exception("해당 번호의 데이터가 없습니다!");
    }
    photoBoardDao.increaseViewCount(no);

    model.put("photoBoard", photoBoard);
    return "/jsp/photoboard/detail.jsp";
  }
  
  @RequestMapping("/photoboard/list")
  public String list(Map<String,Object> model) 
      throws Exception {

    List<PhotoBoard> photoBoards = photoBoardDao.findAll();
    model.put("photoBoards", photoBoards);
    return "/jsp/photoboard/list.jsp";
  }
  
  @RequestMapping("/photoboard/update")
  public String update(
      HttpServletRequest request, 
      PhotoBoard photoBoard,
      Part[] filePath) throws Exception {

    String uploadDir = request.getServletContext().getRealPath("/upload/photoboard");

    // 트랜잭션 동작을 정의한다.
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    // 정의된 트랜잭션 동작에 따라 작업을 수행할 트랜잭션 객체를 준비한다. 
    TransactionStatus status = txManager.getTransaction(def);

    try {
      photoBoardDao.update(photoBoard);
      photoFileDao.deleteAll(photoBoard.getNo());

      int count = 0;
      for (Part part : filePath) {
        if (part.getSize() == 0)
          continue;
        
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
      return "redirect:list";

    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }
}
