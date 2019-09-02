package com.eomcs.lms;

import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import com.eomcs.util.PlatformTransactionManager;
import com.eomcs.util.SqlSessionFactoryProxy;

// Spring IoC 컨테이너에게 알려줄 설정 정보를 애노테이션을 이용하여 
// 이 클래스에 저장해 둔다.
// 
public class AppConfig {
  
  @Bean // Spring IoC 컨테이너에게 이 메서드를 호출하여 리턴 값을 보관하라고 표시한다.
  private SqlSessionFactory sqlSessionFactory() throws Exception {
    // Mybatis 객체 준비
    InputStream inputStream = 
        Resources.getResourceAsStream("com/eomcs/lms/conf/mybatis-config.xml");
    SqlSessionFactory sqlSessionFactory =new SqlSessionFactoryProxy(
        new SqlSessionFactoryBuilder().build(inputStream));
    return sqlSessionFactory;
  }
  
  @Bean
  private PlatformTransactionManager transactionManager() throws Exception {
    PlatformTransactionManager txManager = 
        new PlatformTransactionManager(
            (SqlSessionFactory)objPool.get("sqlSessionFactory"));
  }
}
