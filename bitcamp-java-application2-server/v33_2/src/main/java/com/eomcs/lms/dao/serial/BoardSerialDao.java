package com.eomcs.lms.dao.serial;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

public class BoardSerialDao extends AbstractDataSerializer<Board,Integer> 
    implements BoardDao {
  
  public BoardSerialDao(String file) throws ClassNotFoundException {
    super(file);
    
    try {
      loadData();
      System.out.println("게시물 데이터 로딩 완료!");
      
    } catch (IOException e) {
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








