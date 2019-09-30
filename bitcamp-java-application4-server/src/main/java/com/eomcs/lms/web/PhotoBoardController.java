package com.eomcs.lms.web;

import java.io.File;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;

@Controller
@RequestMapping("/photoboard")
public class PhotoBoardController {
  
  @Resource private PlatformTransactionManager txManager;
  @Resource private PhotoBoardDao photoBoardDao;
  @Resource private PhotoFileDao photoFileDao;
  
  @GetMapping("form")
  public void form() {
  }
  
  @PostMapping("add")
  public String add(
      HttpServletRequest request, 
      PhotoBoard photoBoard,
      MultipartFile[] filePath) throws Exception {
    
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
      for (MultipartFile file : filePath) {
        if (file.isEmpty())
          continue;
        
        // 클라이언트가 보낸 파일을 디스크에 저장한다.
        String filename = UUID.randomUUID().toString();
        file.transferTo(new File(uploadDir + "/" + filename));
        
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
  
  @GetMapping("delete")
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
  
  @GetMapping("detail")
  public void detail(Model model, int no) 
      throws Exception {

    PhotoBoard photoBoard = photoBoardDao.findWithFilesBy(no);
    if (photoBoard == null) {
      throw new Exception("해당 번호의 데이터가 없습니다!");
    }
    photoBoardDao.increaseViewCount(no);

    model.addAttribute("photoBoard", photoBoard);
  }
  
  @GetMapping("list")
  public void list(Model model) 
      throws Exception {

    List<PhotoBoard> photoBoards = photoBoardDao.findAll();
    model.addAttribute("photoBoards", photoBoards);
  }
  
  @PostMapping("update")
  public String update(
      HttpServletRequest request, 
      PhotoBoard photoBoard,
      MultipartFile[] filePath) throws Exception {

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
      for (MultipartFile file : filePath) {
        if (file.isEmpty())
          continue;
        
        // 클라이언트가 보낸 파일을 디스크에 저장한다.
        String filename = UUID.randomUUID().toString();
        file.transferTo(new File(uploadDir + "/" + filename));

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
