package com.eomcs.lms;

import java.sql.Date;
import java.util.Scanner;

public class BoardHandler {
  static Board[] boards = new Board[100];
  static int boardsSize = 0;
  static Scanner keyScan;
  
  static void listBoard() {
    for (int i = 0; i < boardsSize; i++) {
      Board board = boards[i];
      System.out.printf("%s, %s, %s, %s\n", 
          board.no, board.contents, board.createdDate, board.viewCount);
    }
  }

  static void addBoard() {
    Board board = new Board();
    
    board.no = Input.getIntValue("번호? ");
    board.contents = Input.getStringValue("내용? ");
    board.createdDate = new Date(System.currentTimeMillis()); 
    
    boards[boardsSize++] = board;
    System.out.println("저장하였습니다.");
  }
}
