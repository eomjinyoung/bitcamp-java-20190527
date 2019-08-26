package com.eomcs.lms.dao.mariadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.util.DataSource;

public class PhotoBoardDaoImpl implements PhotoBoardDao {

  DataSource dataSource;
  
  public PhotoBoardDaoImpl(DataSource conFactory) {
    this.dataSource = conFactory;
  }

  @Override
  public int insert(PhotoBoard photoBoard) throws Exception {
    try (Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement()) {

      // insert 한 후에 자동 생성된 PK 값을 리턴 받고 싶다면 
      // 두 번째 파라미터에 상수를 지정해야 한다.
      int count = stmt.executeUpdate(
          "insert into lms_photo(lesson_id,titl)"
              + " values(" + photoBoard.getLessonNo()
              + ",'" + photoBoard.getTitle() + "')",
          Statement.RETURN_GENERATED_KEYS);
      
      // insert 한 후 자동 생성된 PK 값을 꺼내려면 
      // 다음 메서드를 호출하여 ResultSet을 얻어야 한다.
      try (ResultSet rs = stmt.getGeneratedKeys();) {
      
        // ResultSet을 통해 자동 생성된 값을 꺼내라.
        if (rs.next()) {
          int autoIncrementPK = rs.getInt(1);
          
          // 호출자에게 리턴하는 방법:
          // 파라미터로 받은 PhotoBoard 객체에 꺼꾸로 저장하라!
          photoBoard.setNo(autoIncrementPK);
        }
      }
      
      return count;
    }
  }

  @Override
  public List<PhotoBoard> findAll() throws Exception {
    try (Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select * from lms_photo order by photo_id desc")) {

      ArrayList<PhotoBoard> list = new ArrayList<>();
      
      while (rs.next()) {
        PhotoBoard photoBoard = new PhotoBoard();
        photoBoard.setNo(rs.getInt("photo_id"));
        photoBoard.setTitle(rs.getString("titl"));
        photoBoard.setCreatedDate(rs.getDate("cdt"));
        photoBoard.setViewCount(rs.getInt("vw_cnt"));
        photoBoard.setLessonNo(rs.getInt("lesson_id"));
        list.add(photoBoard);
      }
      return list;
    }
  }

  @Override
  public PhotoBoard findBy(int no) throws Exception {
    try (Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select * from lms_photo where photo_id=" + no)) {

      if (rs.next()) {
        PhotoBoard photoBoard = new PhotoBoard();
        photoBoard.setNo(rs.getInt("photo_id"));
        photoBoard.setTitle(rs.getString("titl"));
        photoBoard.setCreatedDate(rs.getDate("cdt"));
        photoBoard.setViewCount(rs.getInt("vw_cnt"));
        photoBoard.setLessonNo(rs.getInt("lesson_id"));
        
        // 게시글을 찾았으면 조회수를 증가시킨다.
        stmt.executeUpdate("update lms_photo set"
            + " vw_cnt=vw_cnt + 1 where photo_id=" + no);
        
        return photoBoard;
        
      } else {
        return null;
      }
    }
  }

  @Override
  public int update(PhotoBoard photoBoard) throws Exception {
    try (Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement()) {

      return stmt.executeUpdate("update lms_photo set"
          + " titl='" + photoBoard.getTitle()
          + "' where photo_id=" + photoBoard.getNo());
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement()) {

      return stmt.executeUpdate("delete from lms_photo where photo_id=" + no);
    }
  }
  
  public static void main(String[] args) throws Exception {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost/bitcampdb?user=bitcamp&password=1111");) {
    
      //PhotoBoardDao dao = new PhotoBoardDaoImpl(con);
    
      //1) insert() 테스트
      /*
      PhotoBoard b = new PhotoBoard();
      b.setLessonNo(101);
      b.setTitle("사진 게시글 테스트2");
      
      dao.insert(b);
      */
      
      //2) findAll() 테스트
      /*
      List<PhotoBoard> list = dao.findAll();
      for (PhotoBoard b : list) {
        System.out.println(b);
      }
      */
      
      //3) findBy() 테스트
      /*
      PhotoBoard b = dao.findBy(9);
      System.out.println(b);
      */
      
      //4) update() 테스트
      /*
      PhotoBoard b = new PhotoBoard();
      b.setNo(9);
      b.setTitle("제목 변경");
      dao.update(b);
      */
      
      //5) delete() 테스트
      /*
      dao.delete(9);
      */
      
      System.out.println("실행 완료!");
    }
  }

}










