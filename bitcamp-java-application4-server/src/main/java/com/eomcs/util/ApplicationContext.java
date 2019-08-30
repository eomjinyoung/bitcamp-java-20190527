package com.eomcs.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;

// 자바 객체를 자동 생성하여 관리하는 역할
// 1단계: App 클래스에서 객체 생성 코드를 분리하기
// 2단계: 특정 패키지의 클래스에 대해 인스턴스 생성하기
public class ApplicationContext {
  
  HashMap<String,Object> objPool = new HashMap<>();
  
  // 자동 생성할 타입(클래스 정보) 목록
  ArrayList<Class<?>> classes = new ArrayList<>();
  
  public ApplicationContext(String packageName) throws Exception {
    
    createSqlSessionFactory();
    createTransactionManager();
    createDao();
    
    // 파라미터에 주어진 패키지를 뒤져서 Command 인터페이스를 구현한 클래스를 찾는다.
    // => 찾은 클래스의 인스턴스를 생성한다.
    findCommandClass();
    createCommand();
  }
  
  public Object getBean(String name) throws RuntimeException {
    Object obj = objPool.get(name);
    if (obj == null) 
      throw new RuntimeException("해당 이름의 빈을 찾을 수 없습니다.");
    return obj;
  }
  
  public void addBean(String name, Object obj) {
    if (name == null || obj == null)
      return;
    
    objPool.put(name, obj);
  }
  
  private void createSqlSessionFactory() throws Exception {
    // Mybatis 객체 준비
    InputStream inputStream = 
        Resources.getResourceAsStream("com/eomcs/lms/conf/mybatis-config.xml");
    SqlSessionFactory sqlSessionFactory =new SqlSessionFactoryProxy(
        new SqlSessionFactoryBuilder().build(inputStream));
    objPool.put("sqlSessionFactory", sqlSessionFactory);
  }
  
  private void createTransactionManager() throws Exception {
    PlatformTransactionManager txManager = 
        new PlatformTransactionManager(
            (SqlSessionFactory)objPool.get("sqlSessionFactory"));
  }
  
  private void createDao() throws Exception {
    // DAO 구현체 생성기를 준비한다.
    MybatisDaoFactory daoFactory = new MybatisDaoFactory(
        (SqlSessionFactory)objPool.get("sqlSessionFactory"));
    
    // 데이터 처리 객체를 준비한다.
    objPool.put("memberDao", daoFactory.createDao(MemberDao.class));
    objPool.put("lessonDao", daoFactory.createDao(LessonDao.class));
    objPool.put("photoBoardDao", daoFactory.createDao(PhotoBoardDao.class));
    objPool.put("photoFileDao", daoFactory.createDao(PhotoFileDao.class));
  }
}







