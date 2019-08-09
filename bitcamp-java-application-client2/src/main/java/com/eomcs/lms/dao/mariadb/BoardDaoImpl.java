package com.eomcs.lms.dao.mariadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

public class BoardDaoImpl implements BoardDao {

  @Override
  public int insert(Board board) throws Exception {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost/bitcampdb?user=bitcamp&password=1111");
        Statement stmt = con.createStatement()) {

      return stmt.executeUpdate(
          "insert into lms_board(conts)"
              + " values('" + board.getContents() + "')");
    }
  }

  @Override
  public List<Board> findAll() throws Exception {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost/bitcampdb?user=bitcamp&password=1111");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select * from lms_board order by board_id desc")) {

      ArrayList<Board> list = new ArrayList<>();
      
      while (rs.next()) {
        Board board = new Board();
        board.setNo(rs.getInt("board_id"));
        board.setContents(rs.getString("conts"));
        board.setCreatedDate(rs.getDate("cdt"));
        board.setViewCount(rs.getInt("vw_cnt"));
        
        list.add(board);
      }
      return list;
    }
  }

  @Override
  public Board findBy(int no) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int update(Board board) throws Exception {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int delete(int no) throws Exception {
    // TODO Auto-generated method stub
    return 0;
  }

}
