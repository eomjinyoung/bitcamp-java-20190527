package com.eomcs.lms.handler;

import java.sql.Date;
import com.eomcs.lms.domain.Board;
import com.eomcs.util.ArrayList;
import com.eomcs.util.Input;

public class BoardHandler {
  private ArrayList boardList = new ArrayList();
  private Input input;
  
  public BoardHandler(Input input) {
    this.input = input;
  }
  
  public void listBoard() {
    Object[] list = boardList.toArray();
    for (Object obj : list) {
      Board board = (Board) obj;
      System.out.printf("%s, %s, %s, %s\n", 
          board.getNo(), board.getContents(), 
          board.getCreatedDate(), board.getViewCount());
    }
  }

  public void addBoard() {
    Board board = new Board();
    
    board.setNo(input.getIntValue("번호? "));
    board.setContents(input.getStringValue("내용? "));
    board.setCreatedDate(new Date(System.currentTimeMillis())); 
    
    boardList.add(board);
    System.out.println("저장하였습니다.");
  }
}
