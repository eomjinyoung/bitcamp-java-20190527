package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Connection;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.util.ConnectionFactory;
import com.eomcs.util.Input;

public class PhotoBoardAddCommand implements Command {
  
  private ConnectionFactory conFactory;
  private PhotoBoardDao photoBoardDao;
  private PhotoFileDao photoFileDao;
  
  public PhotoBoardAddCommand(
      ConnectionFactory conFactory,
      PhotoBoardDao photoBoardDao, 
      PhotoFileDao photoFileDao) {
    this.conFactory = conFactory;
    this.photoBoardDao = photoBoardDao;
    this.photoFileDao = photoFileDao;
  }

  @Override
  public void execute(BufferedReader in, PrintStream out) {
    Connection con = null;
    
    try {
      // DAO에게 작업을 시키기 전에 커넥션 팩토리로부터 커넥션을 얻는다.
      // => 그러면 커넥션 팩토리는 스레드 주머니에 커넥션을 담아 둘 것이다.
      // => 그 이후에 DAO가 커넥션 팩토리에게 커넥션을 요구하면
      //    바로 이 스레드 주머니에 들어 있는 커넥션 객체를 리턴할 것이다.
      // => 따라서 이 메서드가 끝날 때 까지 DAO는 같은 커넥션을 사용할 것이다.
      //
      con = conFactory.getConnection();
      
      // DAO가 사용할 커넥션에 대해 AutoCommit을 false로 설정한다.
      // => DBMS 쪽 담당자(스레드)에게 지금부터 수행하는 모든 데이터 변경(insert/update/delete) 작업은
      //    임시 데이터베이스에 보관하라고 명령을 내린다.
      con.setAutoCommit(false);
      
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
      
      // => DBMS 쪽 담당자(스레드)에게 임시 보관된 데이터 변경 결과를 
      //    실제 테이블에 적용할 것을 명령한다.
      con.commit();
      out.println("저장하였습니다.");
      
    } catch (Exception e) {
      // => DBMS 쪽 담당자(스레드)에게 임시 보관된 데이터 변경 결과를 모두 취소할 것을 명령한다.
      try {con.rollback();} catch (Exception e2) {}
      
      out.println("데이터 저장에 실패했습니다!");
      System.out.println(e.getMessage());
      e.printStackTrace();
      
    } finally {
      // 커넥션 객체를 원래의 자동 커밋 상태로 설정한다.
      // => DBMS 쪽 담당자(스레드)에게 이제부터 모든 데이터 변경 작업은 즉시 실행할 것을 명령한다.
      try {con.setAutoCommit(true);} catch (Exception e) {}
    }
     
  }

}
