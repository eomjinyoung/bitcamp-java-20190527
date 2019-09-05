package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.util.Input;

@Component
public class PhotoBoardCommand {
  
  private PlatformTransactionManager txManager;
  private PhotoBoardDao photoBoardDao;
  private PhotoFileDao photoFileDao;
  
  public PhotoBoardCommand(
      PlatformTransactionManager txManager,
      PhotoBoardDao photoBoardDao, 
      PhotoFileDao photoFileDao) {
    this.txManager = txManager;
    this.photoBoardDao = photoBoardDao;
    this.photoFileDao = photoFileDao;
  }

  @RequestMapping("/photoboard/add") // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void add(BufferedReader in, PrintStream out) {
    // 트랜잭션 동작을 정의한다.
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    
    // 정의된 트랜잭션 동작에 따라 작업을 수행할 트랜잭션 객체를 준비한다. 
    TransactionStatus status = txManager.getTransaction(def);

    try {
      PhotoBoard photoBoard = new PhotoBoard();
      photoBoard.setTitle(Input.getStringValue(in, out, "제목? "));
      photoBoard.setLessonNo(Input.getIntValue(in, out, "수업? "));
      
      photoBoardDao.insert(photoBoard);
      
      out.println("최소 한 개의 사진 파일을 등록해야 합니다.");
      out.println("파일명 입력 없이 그냥 엔터를 치면 파일 추가를 마칩니다.");
      out.flush();
      
      int count = 0;
      while (true) {
        String filepath = Input.getStringValue(in, out, "사진 파일? ");
        if (filepath.length() == 0) {
          if (count > 0) {
            break;
          } else { 
            out.println("최소 한 개의 사진 파일을 등록해야 합니다.");
            continue;
          }
        }
        PhotoFile photoFile = new PhotoFile();
        photoFile.setFilePath(filepath);
        photoFile.setBoardNo(photoBoard.getNo());
        photoFileDao.insert(photoFile);
        count++;
      }
      
      txManager.commit(status);
      out.println("저장하였습니다.");
      
    } catch (Exception e) {
      txManager.rollback(status);
      out.println("데이터 저장에 실패했습니다!");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
     
  }
  
  @RequestMapping("/photoboard/delete") // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void delete(BufferedReader in, PrintStream out) {
    // 트랜잭션 동작을 정의한다.
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    
    // 정의된 트랜잭션 동작에 따라 작업을 수행할 트랜잭션 객체를 준비한다. 
    TransactionStatus status = txManager.getTransaction(def);
    
    try {
      int no = Input.getIntValue(in, out, "번호? ");
      
      if (photoBoardDao.findBy(no) == null) {
        out.println("해당 데이터가 없습니다.");
        return;
      }
      
      // 먼저 게시물의 첨부파일을 삭제한다.
      photoFileDao.deleteAll(no);
      
      // 게시물을 삭제한다.
      photoBoardDao.delete(no);
      
      txManager.commit(status);
      out.println("데이터를 삭제하였습니다.");
      
    } catch (Exception e) {
      txManager.rollback(status);
      
      out.println("데이터 삭제에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }
  
  @RequestMapping("/photoboard/detail") // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void detail(BufferedReader in, PrintStream out) {
    try {
      // 클라이언트에게 번호를 요구하여 받는다.
      int no = Input.getIntValue(in, out, "번호? ");
      
      PhotoBoard photoBoard = photoBoardDao.findWithFilesBy(no);
      if (photoBoard == null) {
        out.println("해당 번호의 데이터가 없습니다!");
        return;
      }
      photoBoardDao.increaseViewCount(no);
      
      out.printf("제목: %s\n", photoBoard.getTitle());
      out.printf("작성일: %s\n", photoBoard.getCreatedDate());
      out.printf("조회수: %d\n", photoBoard.getViewCount());
      out.printf("수업: %d\n", photoBoard.getLessonNo());
      out.println("사진 파일:");
      
      List<PhotoFile> files = photoBoard.getFiles();
      for (PhotoFile file : files) {
        out.printf("> %s\n", file.getFilePath());
      }
      
    } catch (Exception e) {
      out.println("데이터 조회에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }
  
  @RequestMapping("/photoboard/list") // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void list(BufferedReader in, PrintStream out) {
    try {
      List<PhotoBoard> photoBoards = photoBoardDao.findAll();
      for (PhotoBoard photoBoard : photoBoards) {
        out.printf("%d, %-30s, %s, %d, %d\n", 
            photoBoard.getNo(), 
            photoBoard.getTitle(), 
            photoBoard.getCreatedDate(), 
            photoBoard.getViewCount(),
            photoBoard.getLessonNo());
      }
      
    } catch (Exception e) {
      out.println("데이터 목록 조회에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }
  
  @RequestMapping("/photoboard/update") // 클라이언트 요청이 들어 왔을 때 이 메서드를 호출하라고 표시한다.
  public void update(BufferedReader in, PrintStream out) {
    // 트랜잭션 동작을 정의한다.
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    
    // 정의된 트랜잭션 동작에 따라 작업을 수행할 트랜잭션 객체를 준비한다. 
    TransactionStatus status = txManager.getTransaction(def);
    
    try {
      int no = Input.getIntValue(in, out, "번호? ");

      PhotoBoard photoBoard = photoBoardDao.findBy(no);
      if (photoBoard == null) {
        out.println("해당 번호의 데이터가 없습니다!");
        return;
      }

      out.println("제목을 입력하지 않으면 이전 제목을 유지합니다.");
      String str = Input.getStringValue(in, out, 
          String.format("제목(%s)? ", photoBoard.getTitle()));

      // 제목을 입력했으면 사진 게시글의 제목을 변경한다.
      if (str.length() > 0) {
        photoBoard.setTitle(str);
        photoBoardDao.update(photoBoard);
        out.println("게시물의 제목을 변경하였습니다.");
      }

      // 이전에 등록한 파일 목록을 출력한다.
      out.println("사진 파일:");
      List<PhotoFile> files = photoFileDao.findAll(no);
      for (PhotoFile file : files) {
        out.printf("> %s\n", file.getFilePath());
      }

      // 파일을 변경할 지 여부를 묻는다.
      out.println("사진은 일부만 변경할 수 없습니다.");
      out.println("전체를 새로 등록해야 합니다.");
      String response = Input.getStringValue(in, out, 
          "사진을 변경하시겠습니까?(y/N)");

      if (!response.equalsIgnoreCase("y")) {
        out.println("파일 변경을 취소합니다.");
        return;
      }
      
      // 기존 사진 파일을 삭제한다.
      photoFileDao.deleteAll(no);

      out.println("최소 한 개의 사진 파일을 등록해야 합니다.");
      out.println("파일명 입력 없이 그냥 엔터를 치면 파일 추가를 마칩니다.");
      out.flush();

      int count = 0;
      while (true) {
        String filepath = Input.getStringValue(in, out, "사진 파일? ");
        if (filepath.length() == 0) {
          if (count > 0) {
            break;
          } else { 
            out.println("최소 한 개의 사진 파일을 등록해야 합니다.");
            continue;
          }
        }
        PhotoFile photoFile = new PhotoFile();
        photoFile.setFilePath(filepath);
        photoFile.setBoardNo(photoBoard.getNo());
        photoFileDao.insert(photoFile);
        count++;
      }

      txManager.commit(status);
      out.println("사진을 변경하였습니다.");
      
    } catch (Exception e) {
      txManager.rollback(status);
      
      out.println("데이터 변경에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }

}
