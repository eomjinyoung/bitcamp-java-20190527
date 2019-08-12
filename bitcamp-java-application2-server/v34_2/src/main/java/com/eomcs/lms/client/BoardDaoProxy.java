package com.eomcs.lms.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

// Stateful 통신 방식을 Stateless 통신 방식으로 변경한다.
// => 매번 요청할 때마다 서버와 연결한다.
// => 서버의 응답을 받으면 연결을 끊는다.
// 
public class BoardDaoProxy implements BoardDao {

  String host;
  int port;

  public BoardDaoProxy(String host, int port) {
    this.host = host;
    this.port = port;
  }

  @Override
  public int insert(Board board) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

      out.writeUTF("/board/add");
      out.writeObject(board);
      out.flush();

      if (!in.readUTF().equals("ok"))
        throw new Exception(in.readUTF());

      return 1;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Board> findAll() throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

      out.writeUTF("/board/list");
      out.flush();

      if (!in.readUTF().equals("ok"))
        throw new Exception(in.readUTF());

      return (List<Board>)in.readObject();
    }
  }

  @Override
  public Board findBy(int no) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

      out.writeUTF("/board/detail");
      out.writeInt(no);
      out.flush();

      if (!in.readUTF().equals("ok"))
        throw new Exception(in.readUTF());

      return (Board)in.readObject();
    }
  }

  @Override
  public int update(Board board) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {


      out.writeUTF("/board/update");
      out.writeObject(board);
      out.flush();

      if (!in.readUTF().equals("ok"))
        throw new Exception(in.readUTF());

      return 1;
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

      out.writeUTF("/board/delete");
      out.writeInt(no);
      out.flush();

      if (!in.readUTF().equals("ok"))
        throw new Exception(in.readUTF());

      return 1;
    }
  }
  
  public static void main(String[] args) throws Exception {
//    BoardDaoProxy daoProxy = new BoardDaoProxy("localhost", 8888);
    
    // 입력 테스트
//    Board board = new Board();
//    board.setNo(100);
//    board.setContents("okokokok");
//
//    daoProxy.insert(board);
//    System.out.println("입력 성공!");
    
    // 조회 테스트
//    Board board = daoProxy.findBy(101);
//    System.out.println(board);
    
    // 목록 조회 테스트
//    List<Board> boards = daoProxy.findAll();
//    for (Board board : boards) {
//      System.out.println(board);
//    }

    // 변경 테스트
//    Board board = new Board();
//    board.setNo(100);
//    board.setContents("오호라... 그렇군요!");
//    
//    daoProxy.update(board);
//    
//    Board board2 = daoProxy.findBy(100);
//    System.out.println(board2);
    
    // 삭제 테스트
//    daoProxy.delete(100);
//    System.out.println("삭제 완료!");
    
  }

}









