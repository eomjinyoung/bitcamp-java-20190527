package com.eomcs.lms.dao.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;
import com.eomcs.util.DataSource;

public class BoardDaoImpl implements BoardDao {

  SqlSessionFactory sqlSessionFactory;
  DataSource dataSource;
  
  public BoardDaoImpl(
      DataSource conFactory, 
      SqlSessionFactory sqlSessionFactory) {
    
    this.dataSource = conFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public int insert(Board board) throws Exception {
    try (Connection con = dataSource.getConnection();
        PreparedStatement stmt = con.prepareStatement(
            "insert into lms_board(conts)"
                + " values(?)")) {
      
      stmt.setString(1, board.getContents());
      
      return stmt.executeUpdate();
    }
  }

  @Override
  public List<Board> findAll() throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      List<Board> list = sqlSession.selectList("BoardDao.findAll");
      return list;
    }
  }

  @Override
  public Board findBy(int no) throws Exception {
    try (Connection con = dataSource.getConnection();
        PreparedStatement stmt = con.prepareStatement(
            "select * from lms_board where board_id=?")) {
      
      stmt.setInt(1, no);
      
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Board board = new Board();
          board.setNo(rs.getInt("board_id"));
          board.setContents(rs.getString("conts"));
          board.setCreatedDate(rs.getDate("cdt"));
          board.setViewCount(rs.getInt("vw_cnt"));
          
          try (PreparedStatement stmt2 = con.prepareStatement(
              "update lms_board set"
                  + " vw_cnt=vw_cnt + 1 where board_id=?")) {
            stmt2.setInt(1, no);
            stmt2.executeUpdate();
          }
          
          return board;
          
        } else {
          return null;
        }
      }
    }
  }

  @Override
  public int update(Board board) throws Exception {
    try (Connection con = dataSource.getConnection();
        PreparedStatement stmt = con.prepareStatement(
            "update lms_board set"
                + " conts=?"
                + " where board_id=?")) {
      
      stmt.setString(1, board.getContents());
      stmt.setInt(2, board.getNo());
      
      return stmt.executeUpdate();
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (Connection con = dataSource.getConnection();
        PreparedStatement stmt = con.prepareStatement(
            "delete from lms_board where board_id=?")) {
      
      stmt.setInt(1, no);
      
      return stmt.executeUpdate();
    }
  }

}
