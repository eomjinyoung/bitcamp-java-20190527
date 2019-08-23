package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.sql.Connection;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.util.ConnectionFactory;
import com.eomcs.util.Input;

public class PhotoBoardDeleteCommand implements Command {
  
  private ConnectionFactory conFactory;
  private PhotoBoardDao photoBoardDao;
  private PhotoFileDao photoFileDao;
  
  public PhotoBoardDeleteCommand(
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
      con = conFactory.getConnection();
      
      con.setAutoCommit(false);
      
      int no = Input.getIntValue(in, out, "번호? ");
      
      if (photoBoardDao.findBy(no) == null) {
        out.println("해당 데이터가 없습니다.");
        return;
      }
      
      // 먼저 게시물의 첨부파일을 삭제한다.
      photoFileDao.deleteAll(no);
      
      // 게시물을 삭제한다.
      photoBoardDao.delete(no);
      
      con.commit();
      out.println("데이터를 삭제하였습니다.");
      
    } catch (Exception e) {
      try {con.rollback();} catch (Exception e2) {}
      
      out.println("데이터 삭제에 실패했습니다!");
      System.out.println(e.getMessage());
      
    } finally {
      try {con.setAutoCommit(true);} catch (Exception e) {}
    }
  }
}
