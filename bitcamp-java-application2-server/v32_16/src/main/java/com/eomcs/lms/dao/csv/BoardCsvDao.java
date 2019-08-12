package com.eomcs.lms.dao.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

public class BoardCsvDao extends AbstractCsvDataSerializer<Board,Integer> 
    implements BoardDao {
  
  public BoardCsvDao(String file) {
    super(file);
    
    try {
      loadData();
      System.out.println("게시물 데이터 로딩 완료!");
      
    } catch (Exception e) {
      System.out.println("게시물 데이터 로딩 중 오류 발생!");
    }
  }
  
  @Override
  public void saveData() {
    try {
      super.saveData();
      System.out.println("게시물 데이터 저장 완료!");
      
    } catch (FileNotFoundException e) {
      System.out.println("파일을 생성할 수 없습니다!");

    } catch (IOException e) {
      System.out.println("파일에 데이터를 출력하는 중에 오류 발생!");
      e.printStackTrace();
    }
  }
  
  // 수퍼 클래스에서 template method(loadSave())를 정의하고 
  // 객체 생성의 구체적인 구현은 서브 클래스에서 완성해야 한다.
  @Override
  protected Board createObject(String[] values) {
    // 파일에서 읽어 들인 데이터의 CSV 형식은 다음과 같다고 가정하자!
    // => 번호,내용,생성일,조회수
    //
    Board board = new Board();
    board.setNo(Integer.parseInt(values[0]));
    board.setContents(values[1]);
    board.setCreatedDate(Date.valueOf(values[2]));
    board.setViewCount(Integer.parseInt(values[3]));
    
    return board;
  }
  
  @Override
  protected String createCSV(Board obj) {
    return String.format("%d,%s,%s,%d",
        obj.getNo(), 
        obj.getContents(),
        obj.getCreatedDate(),
        obj.getViewCount());
  }
  
  @Override
  public int indexOf(Integer key) {
    int i = 0;
    for (Board obj : list) {
      if (obj.getNo() == key) {
        return i;
      }
      i++;
    }
    return -1;
  }
  
  @Override
  public int insert(Board board) throws Exception {
    list.add(board);
    return 1;
  }
  
  @Override
  public List<Board> findAll() throws Exception {
    return list;
  }
  
  @Override
  public Board findBy(int no) throws Exception {
    int index = indexOf(no);
    if (index == -1)
      return null;
    
    return list.get(index);
  }
  
  @Override
  public int update(Board board) throws Exception {
    int index = indexOf(board.getNo());
    if (index == -1)
      return 0;
    
    list.set(index, board);
    return 1;
  }
  
  @Override
  public int delete(int no) throws Exception {
    int index = indexOf(no);
    if (index == -1)
      return 0;
    
    list.remove(index);
    return 1;
  }
  
  
  
}








