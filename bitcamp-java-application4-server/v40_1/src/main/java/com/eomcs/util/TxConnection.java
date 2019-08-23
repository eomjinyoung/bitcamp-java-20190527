package com.eomcs.util;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.ShardingKey;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

// 커넥션 객체의 역할을 수행한다.
// => 실제 메서드가 호출되면 진짜 커넥션 객체에게 전달한다.
// => 왜 만듦?
//    - 원래 커넥션 객체의 메서드의 기능을 변경하기 위함이다.
//    - 프록시 패턴을 사용하면 이런 상황을 처리할 수 있다.
// 프록시 패턴?
// => 다른 객체에게는 원래 객체인 것처럼 보여야 하기 때문에 
//    원래의 객체와 같은 인터페이스를 구현한다.
// => 그러나 실제 일을 할 때는 자신이 하는 것이 아니라 
//    원래 객체에게 떠넘긴다.(위임)
// => 이때 위임하기 전에 추가하거나 변경하고 싶은 작업이 있다면 
//    처리하면 되는 것이다.
//
public class TxConnection implements Connection {

  private Connection origin;
  
  public TxConnection(Connection origin) {
    this.origin = origin;
  }
  
  // 기존 메서드 중에서 close() 대해서는 변경 작업을 추가한다.
  public void close() throws SQLException {
    // close()를 호출하더라도 닫지 않는다.
    // realClose()를 호출할 때에 진짜 닫는다.
    //origin.close();
  }
  
  // TxConnection에만 있는 메서드
  public void realClose() throws SQLException {
    origin.close();
  }

  public <T> T unwrap(Class<T> iface) throws SQLException {
    return origin.unwrap(iface);
  }

  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return origin.isWrapperFor(iface);
  }

  public Statement createStatement() throws SQLException {
    return origin.createStatement();
  }

  public PreparedStatement prepareStatement(String sql) throws SQLException {
    return origin.prepareStatement(sql);
  }

  public CallableStatement prepareCall(String sql) throws SQLException {
    return origin.prepareCall(sql);
  }

  public String nativeSQL(String sql) throws SQLException {
    return origin.nativeSQL(sql);
  }

  public void setAutoCommit(boolean autoCommit) throws SQLException {
    origin.setAutoCommit(autoCommit);
  }

  public boolean getAutoCommit() throws SQLException {
    return origin.getAutoCommit();
  }

  public void commit() throws SQLException {
    origin.commit();
  }

  public void rollback() throws SQLException {
    origin.rollback();
  }

 

  public boolean isClosed() throws SQLException {
    return origin.isClosed();
  }

  public DatabaseMetaData getMetaData() throws SQLException {
    return origin.getMetaData();
  }

  public void setReadOnly(boolean readOnly) throws SQLException {
    origin.setReadOnly(readOnly);
  }

  public boolean isReadOnly() throws SQLException {
    return origin.isReadOnly();
  }

  public void setCatalog(String catalog) throws SQLException {
    origin.setCatalog(catalog);
  }

  public String getCatalog() throws SQLException {
    return origin.getCatalog();
  }

  public void setTransactionIsolation(int level) throws SQLException {
    origin.setTransactionIsolation(level);
  }

  public int getTransactionIsolation() throws SQLException {
    return origin.getTransactionIsolation();
  }

  public SQLWarning getWarnings() throws SQLException {
    return origin.getWarnings();
  }

  public void clearWarnings() throws SQLException {
    origin.clearWarnings();
  }

  public Statement createStatement(int resultSetType, int resultSetConcurrency)
      throws SQLException {
    return origin.createStatement(resultSetType, resultSetConcurrency);
  }

  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
      throws SQLException {
    return origin.prepareStatement(sql, resultSetType, resultSetConcurrency);
  }

  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
      throws SQLException {
    return origin.prepareCall(sql, resultSetType, resultSetConcurrency);
  }

  public Map<String, Class<?>> getTypeMap() throws SQLException {
    return origin.getTypeMap();
  }

  public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
    origin.setTypeMap(map);
  }

  public void setHoldability(int holdability) throws SQLException {
    origin.setHoldability(holdability);
  }

  public int getHoldability() throws SQLException {
    return origin.getHoldability();
  }

  public Savepoint setSavepoint() throws SQLException {
    return origin.setSavepoint();
  }

  public Savepoint setSavepoint(String name) throws SQLException {
    return origin.setSavepoint(name);
  }

  public void rollback(Savepoint savepoint) throws SQLException {
    origin.rollback(savepoint);
  }

  public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    origin.releaseSavepoint(savepoint);
  }

  public Statement createStatement(int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    return origin.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    return origin.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) throws SQLException {
    return origin.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
  }

  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
    return origin.prepareStatement(sql, autoGeneratedKeys);
  }

  public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
    return origin.prepareStatement(sql, columnIndexes);
  }

  public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
    return origin.prepareStatement(sql, columnNames);
  }

  public Clob createClob() throws SQLException {
    return origin.createClob();
  }

  public Blob createBlob() throws SQLException {
    return origin.createBlob();
  }

  public NClob createNClob() throws SQLException {
    return origin.createNClob();
  }

  public SQLXML createSQLXML() throws SQLException {
    return origin.createSQLXML();
  }

  public boolean isValid(int timeout) throws SQLException {
    return origin.isValid(timeout);
  }

  public void setClientInfo(String name, String value) throws SQLClientInfoException {
    origin.setClientInfo(name, value);
  }

  public void setClientInfo(Properties properties) throws SQLClientInfoException {
    origin.setClientInfo(properties);
  }

  public String getClientInfo(String name) throws SQLException {
    return origin.getClientInfo(name);
  }

  public Properties getClientInfo() throws SQLException {
    return origin.getClientInfo();
  }

  public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
    return origin.createArrayOf(typeName, elements);
  }

  public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
    return origin.createStruct(typeName, attributes);
  }

  public void setSchema(String schema) throws SQLException {
    origin.setSchema(schema);
  }

  public String getSchema() throws SQLException {
    return origin.getSchema();
  }

  public void abort(Executor executor) throws SQLException {
    origin.abort(executor);
  }

  public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
    origin.setNetworkTimeout(executor, milliseconds);
  }

  public int getNetworkTimeout() throws SQLException {
    return origin.getNetworkTimeout();
  }

  public void beginRequest() throws SQLException {
    origin.beginRequest();
  }

  public void endRequest() throws SQLException {
    origin.endRequest();
  }

  public boolean setShardingKeyIfValid(ShardingKey shardingKey,
      ShardingKey superShardingKey, int timeout) throws SQLException {
    return origin.setShardingKeyIfValid(shardingKey, superShardingKey, timeout);
  }

  public boolean setShardingKeyIfValid(ShardingKey shardingKey, int timeout)
      throws SQLException {
    return origin.setShardingKeyIfValid(shardingKey, timeout);
  }

  public void setShardingKey(ShardingKey shardingKey, ShardingKey superShardingKey)
      throws SQLException {
    origin.setShardingKey(shardingKey, superShardingKey);
  }

  public void setShardingKey(ShardingKey shardingKey) throws SQLException {
    origin.setShardingKey(shardingKey);
  }
  
  
  
}
