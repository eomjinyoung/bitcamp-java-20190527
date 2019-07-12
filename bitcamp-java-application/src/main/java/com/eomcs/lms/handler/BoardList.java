package com.eomcs.lms.handler;

import com.eomcs.lms.domain.Board;

public class BoardList {
  private Board[] list = new Board[100];
  private int size = 0;
  
  public void add(Board Board) {
    this.list[this.size++] = Board;
  }
  
  public Board[] toArray() {
    Board[] arr = new Board[this.size];
    for (int i = 0; i < this.size; i++) {
      arr[i] = this.list[i];
    }
    return arr;
  }
}
