package com.eomcs.lms.handler;

import java.sql.Date;
import java.util.List;
import com.eomcs.lms.domain.Board;
import com.eomcs.util.Input;

public class BoardAddCommand implements Command {
  
  private List<Board> list;
  private Input input;
  
  public BoardAddCommand(Input input, List<Board> list) {
    this.input = input;
    this.list = list;
  }

  @Override
  public void execute() {
    Board board = new Board();
    
    board.setNo(input.getIntValue("번호? "));
    board.setContents(input.getStringValue("내용? "));
    board.setCreatedDate(new Date(System.currentTimeMillis())); 
    
    list.add(board);
    System.out.println("저장하였습니다.");
  }

}
