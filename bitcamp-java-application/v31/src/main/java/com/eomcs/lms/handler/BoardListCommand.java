package com.eomcs.lms.handler;

import java.util.List;
import com.eomcs.lms.domain.Board;
import com.eomcs.util.Input;

public class BoardListCommand implements Command {
  
  private List<Board> list;
  private Input input;
  
  public BoardListCommand(Input input, List<Board> list) {
    this.input = input;
    this.list = list;
  }
  
  @Override
  public void execute() {
    //Board[] boards = new Board[boardList.size()];
    //boardList.toArray(boards);
    
    Board[] boards = list.toArray(new Board[] {});
    for (Board board : boards) {
      System.out.printf("%s, %s, %s, %s\n", 
          board.getNo(), board.getContents(), 
          board.getCreatedDate(), board.getViewCount());
    }
  }

}
