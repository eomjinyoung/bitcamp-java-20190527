package com.eomcs.lms.servlet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import com.eomcs.lms.Servlet;
import com.eomcs.lms.dao.csv.BoardCsvDao;
import com.eomcs.lms.domain.Board;

// 게시물 요청을 처리하는 담당자
public class BoardServlet implements Servlet {
  
  BoardCsvDao boardDao;
  
  ObjectInputStream in;
  ObjectOutputStream out;
  
  public BoardServlet(ObjectInputStream in, ObjectOutputStream out) 
      throws Exception {
    
    this.in = in;
    this.out = out;
    
    boardDao = new BoardCsvDao("./board.csv");
  }
  
  public void saveData() {
    boardDao.saveData();
  }
  
  @Override
  public void service(String command) throws Exception {
    switch (command) {
      case "/board/add":
        addBoard();
        break;
      case "/board/list":
        listBoard();
        break;
      case "/board/delete":
        deleteBoard();
        break;  
      case "/board/detail":
        detailBoard();
        break;
      case "/board/update":
        updateBoard();
        break;
      default:
        out.writeUTF("fail");
        out.writeUTF("지원하지 않는 명령입니다.");
    }
  }
  
  private void updateBoard() throws Exception {
    Board board = (Board)in.readObject();
    
    // 서버쪽에서 게시글 변경일을 설정해야 한다.
    // => 클라이언트에서 보내 온 날짜는 조작된 날짜일 수 있기 때문이다.
    board.setCreatedDate(new Date(System.currentTimeMillis()));
    
    if (boardDao.modify(board) == 0) {
      fail("해당 번호의 게시물이 없습니다.");
      return;
    }
    out.writeUTF("ok");
  }

  private void detailBoard() throws Exception {
    int no = in.readInt();
    
    Board board = boardDao.get(no);
    if (board == null) {
      fail("해당 번호의 게시물이 없습니다.");
      return;
    }
    out.writeUTF("ok");
    out.writeObject(board);
  }

  private void deleteBoard() throws Exception {
    int no = in.readInt();
    
    if (boardDao.remove(no) == 0) {
      fail("해당 번호의 게시물이 없습니다.");
      return;
    }
    
    out.writeUTF("ok");
  }

  private void addBoard() throws Exception {
    Board board = (Board)in.readObject();
    
    // 서버쪽에서 게시글 생성일을 설정해야 한다.
    // => 클라이언트에서 보내 온 날짜는 조작된 날짜일 수 있기 때문이다.
    board.setCreatedDate(new Date(System.currentTimeMillis()));
    
    if (boardDao.add(board) == 0) {
      fail("게시물을 입력할 수 없습니다.");
      return;
    }
    
    out.writeUTF("ok");
  }
  
  private void listBoard() throws Exception {
    out.writeUTF("ok");
    out.reset(); // 기존에 serialize 했던 객체의 상태를 무시하고 다시 serialize 한다.
    out.writeObject(boardDao.list());
  }

  private void fail(String cause) throws Exception {
    out.writeUTF("fail");
    out.writeUTF(cause);
  }

}
