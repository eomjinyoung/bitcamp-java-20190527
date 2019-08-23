package com.eomcs.util;

import java.sql.Connection;
import java.sql.DriverManager;

// DAO가 사용할 커넥션 객체를 생성해주는 역할
public class ConnectionFactory {
  String jdbcDriver;
  String jdbcUrl;
  String username;
  String password;

  public ConnectionFactory(
      String jdbcDriver, String jdbcUrl, String username, String password) {
    
    this.jdbcDriver = jdbcDriver;
    this.jdbcUrl = jdbcUrl;
    this.username = username;
    this.password = password;
  }

  public Connection getConnection() throws Exception {
    Class.forName(jdbcDriver);
    
    return DriverManager.getConnection(jdbcUrl, username, password);
  }
}





