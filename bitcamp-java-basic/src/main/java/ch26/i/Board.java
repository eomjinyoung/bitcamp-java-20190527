package ch26.i;

import java.sql.Date;

public class Board {
  
  private int no;
  private String title;
  private String contents;
  private Date createdDate;
  private int viewCount;
  
  @Override
  public String toString() {
    return "Board2 [no=" + no + ", title=" + title + ", contents=" + contents + ", createdDate="
        + createdDate + ", viewCount=" + viewCount + "]";
  }
  
  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getContents() {
    return contents;
  }
  public void setContents(String contents) {
    this.contents = contents;
  }
  public Date getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  public int getViewCount() {
    return viewCount;
  }
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }
  
  
}
