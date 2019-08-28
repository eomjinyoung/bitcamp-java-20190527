package com.eomcs.util;

import java.sql.Connection;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;

// => SqlSessionFactory 로서 역할을 수행하기 위해 인터페이스를 구현한다.
// => 실제 작업은 원래 객체에게 맡긴다.
public class SqlSessionFactoryProxy implements SqlSessionFactory {
  
  SqlSessionFactory realFactory;
  
  // 스레드 전용 바구니를 준비한다.
  // => 이 바구니에는 SqlSession을 보관한다. 
  ThreadLocal<SqlSession> localSqlSession = new ThreadLocal<>();
  
  public SqlSessionFactoryProxy(SqlSessionFactory realFactory) {
    this.realFactory = realFactory;
  }
  
  // 스레드에 보관된 SqlSession을 제거한다.
  // => 클라이언트가 접속할 때 스레드를 만들고, 응답을 완료할 때 스레드를 제거한다면 
  //    굳이 스레드에 보관된 SqlSession을 제거할 필요가 없다.
  // => 스레드풀을 사용할 때는 스레드가 응답을 완료한 후 재사용되기 때문에,
  //    다른 클라이언트의 요청을 처리하기 위해 재사용되기 전에 
  //    이전에 사용했던 SqlSession을 제거해야 한다.
  //    왜? 이전에 사용한 SqlSession은 트랜잭션을 종료하는 순간 close()되었기 때문이다.
  //    다시 사용하고 싶어도 사용할 수 없는 상태이다.
  public void clearSession() {
    localSqlSession.remove();
  }

  public void prepareSessionInThread() {
    SqlSession sqlSession = localSqlSession.get();
    
    if (sqlSession != null) // 이미 스레드에 보관되어 있다면 무시한다.
      return; 
      
    // 아직 스레드에 SqlSession을 보관하지 않았다면
    // 실제 공장 객체를 통해 SqlSession을 만든 후, 스레드에 보관한다.
    sqlSession = new SqlSessionProxy(realFactory.openSession());
    localSqlSession.set(sqlSession);
  }
  
  public SqlSession openSession() {
    SqlSession sqlSession = localSqlSession.get();
    
    // 스레드에 SqlSession이 들어 있으면(트랜잭션이 시작된 상태) 그것을 그대로 리턴한다.
    if (sqlSession != null)
      return sqlSession;
    
    // 트랜잭션을 수행하지 않는 상태라면, 자동 커밋을 수행하는 SqlSession을 리터한다.
    return realFactory.openSession(true);
  }

  public SqlSession openSession(boolean autoCommit) {
    return realFactory.openSession(autoCommit);
  }

  public SqlSession openSession(Connection connection) {
    return realFactory.openSession(connection);
  }

  public SqlSession openSession(TransactionIsolationLevel level) {
    return realFactory.openSession(level);
  }

  public SqlSession openSession(ExecutorType execType) {
    return realFactory.openSession(execType);
  }

  public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
    return realFactory.openSession(execType, autoCommit);
  }

  public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
    return realFactory.openSession(execType, level);
  }

  public SqlSession openSession(ExecutorType execType, Connection connection) {
    return realFactory.openSession(execType, connection);
  }

  public Configuration getConfiguration() {
    return realFactory.getConfiguration();
  }
  
  
}
