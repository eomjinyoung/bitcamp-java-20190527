package com.eomcs.lms.dao.mariadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.util.ConnectionFactory;

public class PhotoFileDaoImpl implements PhotoFileDao {

  ConnectionFactory conFactory;
  
  public PhotoFileDaoImpl(ConnectionFactory conFactory) {
    this.conFactory = conFactory;
  }

  @Override
  public int insert(PhotoFile photoFile) throws Exception {
    try (Connection con = conFactory.getConnection();
        Statement stmt = con.createStatement()) {

      return stmt.executeUpdate(
          "insert into lms_photo_file(photo_id, file_path)"
              + " values(" + photoFile.getBoardNo()
              + ",'" + photoFile.getFilePath() + "')");
    }
  }

  @Override
  public List<PhotoFile> findAll(int boardNo) throws Exception {
    try (Connection con = conFactory.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select photo_file_id, photo_id, file_path"
            + " from lms_photo_file"
            + " where photo_id=" + boardNo
            + " order by photo_file_id asc")) {

      ArrayList<PhotoFile> list = new ArrayList<>();
      
      while (rs.next()) {
        PhotoFile photoFile = new PhotoFile();
        photoFile.setNo(rs.getInt("photo_file_id"));
        photoFile.setBoardNo(rs.getInt("photo_id"));
        photoFile.setFilePath(rs.getString("file_path"));
        
        list.add(photoFile);
      }
      return list;
    }
  }

  @Override
  public int deleteAll(int boardNo) throws Exception {
    try (Connection con = conFactory.getConnection();
        Statement stmt = con.createStatement()) {

      return stmt.executeUpdate("delete from lms_photo_file"
          + " where photo_id=" + boardNo);
    }
  }
  
  public static void main(String[] args) throws Exception {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mariadb://localhost/bitcampdb?user=bitcamp&password=1111");) {
    
      //PhotoFileDao dao = new PhotoFileDaoImpl(con);
    
      //1) insert() 테스트
      /*
      PhotoFile b = new PhotoFile();
      b.setBoardNo(6);
      b.setFilePath("ok5.gif");
      
      dao.insert(b);
      */
      
      //2) findAll() 테스트
      /*
      List<PhotoFile> list = dao.findAll(6);
      for (PhotoFile b : list) {
        System.out.println(b);
      }
      */
      
      //3) deleteAll() 테스트
      ///*
      //dao.deleteAll(6);
      //*/
      
      System.out.println("실행 완료!");
    }
  }

}










