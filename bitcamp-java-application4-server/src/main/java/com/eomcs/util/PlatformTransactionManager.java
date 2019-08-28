package com.eomcs.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

// Mybatis의 SqlSessionFactory 객체를 사용하여 트랜잭션을 관리하는 일을 한다. 
public class PlatformTransactionManager {
  
  private SqlSessionFactory sqlSessionFactory;
  
  public PlatformTransactionManager(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  public void beginTransaction() throws Exception {
    // 여기에서 openSession()을 호출하면 
    // 팩토리는 SqlSession 객체를 미리 스레드에 보관해 둘 것이다.
    sqlSessionFactory.openSession();
  }
  
  public void commit() throws Exception {
    // 현재 스레드의 주머니에서 SqlSession을 꺼낸다.
    SqlSession sqlSession = sqlSessionFactory.openSession();
    sqlSession.commit();
    ((SqlSessionProxy)sqlSession).realClose();
  }
  
  public void rollback() throws Exception {
    // 현재 스레드의 주머니에서 SqlSession을 꺼낸다.
    SqlSession sqlSession = sqlSessionFactory.openSession();
    sqlSession.rollback();
    ((SqlSessionProxy)sqlSession).realClose();
  }
}







