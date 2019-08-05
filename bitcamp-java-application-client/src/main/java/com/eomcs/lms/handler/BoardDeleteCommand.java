package com.eomcs.lms.handler;

import com.eomcs.lms.dao.BoardDao;
import com.eomcs.util.Input;

public class BoardDeleteCommand implements Command {
  
  private BoardDao boardDao;
  private Input input;
  
  public BoardDeleteCommand(Input input, BoardDao boardDao) {
    this.input = input;
    this.boardDao = boardDao;
  }
  
  @Override
  public void execute() {
    int no = input.getIntValue("번호? ");
    
    try {
      boardDao.delete(no);
      System.out.println("데이터를 삭제하였습니다.");
      
    } catch (Exception e) {
      System.out.println("데이터 삭제에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }
}
