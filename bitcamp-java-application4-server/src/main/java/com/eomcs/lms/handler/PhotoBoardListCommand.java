package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.List;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.domain.PhotoBoard;

public class PhotoBoardListCommand implements Command {
  
  private PhotoBoardDao photoBoardDao;
  
  public PhotoBoardListCommand(PhotoBoardDao photoBoardDao) {
    this.photoBoardDao = photoBoardDao;
  }
  
  public String getCommandName() {
    return "/photoboard/list";
  }
  
  @Override
  public void execute(BufferedReader in, PrintStream out) {
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

}
