package com.eomcs.lms.dao.mariadb;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

public class BoardDaoImpl implements BoardDao {

  SqlSessionFactory sqlSessionFactory;
  
  public BoardDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public int insert(Board board) throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
      int count = sqlSession.insert("BoardDao.insert", board);
      sqlSession.commit();
      return count;
      
    } catch (Exception e) {
      sqlSession.rollback();
      throw e;
      
    } finally {
      sqlSession.close();
    }
  }

  @Override
  public List<Board> findAll() throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("BoardDao.findAll");
    }
  }

  @Override
  public Board findBy(int no) throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
      Board board = sqlSession.selectOne("BoardDao.findBy", no);
      if (board != null) {
          sqlSession.update("BoardDao.increaseViewCount", no);
          sqlSession.commit();
      }
      return board;
      
    } catch (Exception e) {
      sqlSession.rollback();
      throw e;
      
    } finally {
      sqlSession.close();
    }
  }

  @Override
  public int update(Board board) throws Exception {
    // openSession()을 호출할 때 다음과 같이 autoCommit을 true로 설정할 수 있다.
    // 그러면 commit()을 따로 호출하지 않아도 update()를 실행할 때 자동으로 commit 된다.
    try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
      return sqlSession.update("BoardDao.update", board);
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
      return sqlSession.delete("BoardDao.delete", no);
    }
  }

}
