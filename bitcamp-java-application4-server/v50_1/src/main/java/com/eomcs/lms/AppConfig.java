package com.eomcs.lms;

import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.util.MybatisDaoFactory;
import com.eomcs.util.PlatformTransactionManager;
import com.eomcs.util.SqlSessionFactoryProxy;

// Spring IoC 컨테이너에게 알려줄 설정 정보를 애노테이션을 이용하여 
// 이 클래스에 저장해 둔다.
// 

// com.eomcs.lms 패키지에서 @Component가 붙은 클래스를 찾아 인스턴스를 자동으로 생성하게 한다.
@ComponentScan("com.eomcs.lms")
public class AppConfig {
  
  @Bean // Spring IoC 컨테이너에게 이 메서드를 호출하여 리턴 값을 보관하라고 표시한다.
  private SqlSessionFactory sqlSessionFactory() throws Exception {
    System.out.println("AppConfig.sqlSessionFactory() 호출됨!");
    // Mybatis 객체 준비
    InputStream inputStream = 
        Resources.getResourceAsStream("com/eomcs/lms/conf/mybatis-config.xml");
    SqlSessionFactory sqlSessionFactory =new SqlSessionFactoryProxy(
        new SqlSessionFactoryBuilder().build(inputStream));
    return sqlSessionFactory;
  }
  
  // @Bean을 붙인(@Bean으로 표시한) 메서드는 Spring IoC 컨테이너가 호출한다.
  // 이때 Spring IoC 컨테이너가 갖고 있는 값을 받고 싶다면,
  // 파라미터로 그 타입의 변수를 선언하면 된다.
  @Bean
  private PlatformTransactionManager transactionManager(
      SqlSessionFactory sqlSessionFactory) throws Exception {
    System.out.println("AppConfig.transactionManager() 호출됨!");
    PlatformTransactionManager txManager = 
        new PlatformTransactionManager(sqlSessionFactory);
    return txManager;
  }
  
  @Bean
  private MybatisDaoFactory daoFactory(
      SqlSessionFactory sqlSessionFactory) throws Exception {
    System.out.println("AppConfig.daoFactory() 호출됨!");
    // DAO 구현체 생성기를 준비한다.
    MybatisDaoFactory daoFactory = new MybatisDaoFactory(sqlSessionFactory);
    return daoFactory;
  }
  
  @Bean
  private BoardDao  boardDao(
      MybatisDaoFactory mybatisDaoFactory) throws Exception {
    System.out.println("AppConfig.boardDao() 호출됨!");
    return mybatisDaoFactory.createDao(BoardDao.class);
  }
  
  @Bean
  private MemberDao  memberDao(
      MybatisDaoFactory mybatisDaoFactory) throws Exception {
    System.out.println("AppConfig.memberDao() 호출됨!");
    return mybatisDaoFactory.createDao(MemberDao.class);
  }
  
  @Bean
  private LessonDao  lessonDao(
      MybatisDaoFactory mybatisDaoFactory) throws Exception {
    System.out.println("AppConfig.lessonDao() 호출됨!");
    return mybatisDaoFactory.createDao(LessonDao.class);
  }
  
  @Bean
  private PhotoBoardDao  photoBoardDao(
      MybatisDaoFactory mybatisDaoFactory) throws Exception {
    System.out.println("AppConfig.photoBoardDao() 호출됨!");
    return mybatisDaoFactory.createDao(PhotoBoardDao.class);
  }
  
  @Bean
  private PhotoFileDao  photoFileDao(
      MybatisDaoFactory mybatisDaoFactory) throws Exception {
    System.out.println("AppConfig.photoFileDao() 호출됨!");
    return mybatisDaoFactory.createDao(PhotoFileDao.class);
  }
}








