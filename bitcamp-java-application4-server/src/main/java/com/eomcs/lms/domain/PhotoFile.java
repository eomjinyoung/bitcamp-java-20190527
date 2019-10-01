package com.eomcs.lms.domain;

import java.io.Serializable;

public class PhotoFile implements Serializable {
  private static final long serialVersionUID = 1L;

  private int no;
  
  private String filePath;
  
  private int boardNo;
  
  @Override
  public String toString() {
    return "PhotoFile [no=" + no + ", filePath=" + filePath + ", boardNo=" + boardNo + "]";
  }
  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getFilePath() {
    return filePath;
  }
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
  public int getBoardNo() {
    return boardNo;
  }
  public void setBoardNo(int boardNo) {
    this.boardNo = boardNo;
  }
  
  
}
