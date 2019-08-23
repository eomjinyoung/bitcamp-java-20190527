package com.eomcs.util;

import java.sql.Connection;

// DB 커넥션 객체를 사용하여 트랜잭션을 관리하는 일을 한다. 
public class PlatformTransactionManager {
  
  private ConnectionFactory conFactory;
  
  public PlatformTransactionManager(ConnectionFactory conFactory) {
    this.conFactory = conFactory;
  }
  
  public void beginTransaction() throws Exception {
    // ConnectionFactory는 커텍션 객체를 준비한 후 
    // 그 커넥션 객체(의 주소)를 현재 스레드의 주머니에 보관하고, 리턴한다.
    Connection con = conFactory.getConnection();
    con.setAutoCommit(false);
  }
  
  public void commit() throws Exception {
    // 현재 스레드의 주머니에서 커넥션 객체를 꺼낸다.
    Connection con = conFactory.getConnection();
    con.commit();
    con.setAutoCommit(true);
  }
  
  public void rollback() throws Exception {
    // 현재 스레드의 주머니에서 커넥션 객체를 꺼낸다.
    Connection con = conFactory.getConnection();
    con.rollback();
    con.setAutoCommit(true);
  }
}







