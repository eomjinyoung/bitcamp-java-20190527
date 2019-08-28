package com.eomcs.lms.dao.mariadb;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.domain.PhotoBoard;

public class PhotoBoardDaoImpl implements PhotoBoardDao {

  SqlSessionFactory sqlSessionFactory;
  
  public PhotoBoardDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public int insert(PhotoBoard photoBoard) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.insert("PhotoBoardDao.insert", photoBoard);
    }
  }

  @Override
  public List<PhotoBoard> findAll() throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("PhotoBoardDao.findAll");
    }
  }

  @Override
  public PhotoBoard findBy(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      PhotoBoard board = sqlSession.selectOne("PhotoBoardDao.findBy", no);
      if (board != null) {
        sqlSession.update("PhotoBoardDao.increaseViewCount", no);
      }
      return board;
    }
  }

  @Override
  public int update(PhotoBoard photoBoard) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.update("PhotoBoardDao.update", photoBoard);
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.delete("PhotoBoardDao.delete", no);
    }
  }
  
}










