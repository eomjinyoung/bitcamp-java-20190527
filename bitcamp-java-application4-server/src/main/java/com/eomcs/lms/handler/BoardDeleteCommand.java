package com.eomcs.lms.handler;

import java.io.BufferedReader;
import java.io.PrintStream;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.util.Input;

public class BoardDeleteCommand implements Command {
  
  private BoardDao boardDao;
  
  public BoardDeleteCommand(BoardDao boardDao) {
    this.boardDao = boardDao;
  }
  
  @Override
  public void execute(BufferedReader in, PrintStream out) {
    try {
      int no = Input.getIntValue(in, out, "번호? ");
      
      if (boardDao.delete(no) > 0) {
        out.println("데이터를 삭제하였습니다.");
      } else {
        out.println("해당 데이터가 없습니다.");
      }
      
    } catch (Exception e) {
      out.println("데이터 삭제에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }
}
