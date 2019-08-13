package com.eomcs.lms.handler;

import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;
import com.eomcs.util.Input;

public class BoardDetailCommand implements Command {
  
  private BoardDao boardDao;
  private Input input;
  
  public BoardDetailCommand(Input input, BoardDao boardDao) {
    this.input = input;
    this.boardDao = boardDao;
  }
  
  @Override
  public void execute() {
    int no = input.getIntValue("번호? ");

    try {
      // 사용자가 입력한 번호를 가지고 목록에서 그 번호에 해당하는 Board 객체를 찾는다.
      Board board = boardDao.findBy(no);
      
      if (board == null) {
        System.out.println("해당 번호의 데이터가 없습니다!");
        return;
      }
      
      System.out.printf("내용: %s\n", board.getContents());
      System.out.printf("작성일: %s\n", board.getCreatedDate());
      
    } catch (Exception e) {
      System.out.println("데이터 조회에 실패했습니다!");
      System.out.println(e.getMessage());
    }
  }

}
