package com.eomcs.lms.servlet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

// 게시물 요청을 처리하는 담당자
public class BoardServlet implements Servlet {
  
  // 게시물 DAO를 교체하기 쉽도록 인터페이스의 레퍼런스로 선언한다.
  BoardDao boardDao;
  
  ObjectInputStream in;
  ObjectOutputStream out;
  
  public BoardServlet(BoardDao boardDao, ObjectInputStream in, ObjectOutputStream out) 
      throws Exception {
    
    this.in = in;
    this.out = out;
    
    // 서블릿이 사용할 DAO를 직접 만들지 않고 외부에서 주입 받아 사용한다.
    // 이렇게 의존하는 객체를 외부에서 주입 받아 사용하는 방법을
    // "의존성 주입(Dependency Injection; DI)"이라 부른다.
    // => 그래야만 의존 객체를 교체하기 쉽다.
    //
    this.boardDao = boardDao;
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
    
    if (boardDao.update(board) == 0) {
      fail("해당 번호의 게시물이 없습니다.");
      return;
    }
    out.writeUTF("ok");
  }

  private void detailBoard() throws Exception {
    int no = in.readInt();
    
    Board board = boardDao.findBy(no);
    if (board == null) {
      fail("해당 번호의 게시물이 없습니다.");
      return;
    }
    out.writeUTF("ok");
    out.writeObject(board);
  }

  private void deleteBoard() throws Exception {
    int no = in.readInt();
    
    if (boardDao.delete(no) == 0) {
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
    
    if (boardDao.insert(board) == 0) {
      fail("게시물을 입력할 수 없습니다.");
      return;
    }
    
    out.writeUTF("ok");
  }
  
  private void listBoard() throws Exception {
    out.writeUTF("ok");
    out.reset(); // 기존에 serialize 했던 객체의 상태를 무시하고 다시 serialize 한다.
    out.writeObject(boardDao.findAll());
  }

  private void fail(String cause) throws Exception {
    out.writeUTF("fail");
    out.writeUTF(cause);
  }

}
