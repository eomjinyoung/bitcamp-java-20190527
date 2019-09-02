package com.eomcs.util;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class MybatisDaoFactory {
  SqlSessionFactory sqlSessionFactory;
  
  public MybatisDaoFactory(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  @SuppressWarnings("unchecked")
  public <T> T createDao(Class<T> clazz) {
    return (T) Proxy.newProxyInstance(
        
        clazz.getClassLoader(),
        
        new Class[] {clazz}, 
        
        (Object proxy, Method method, Object[] args) -> {
          // InvocationHandler 구현체의 람다(lambda) 메서드
          // 자동으로 생성된 DAO 구현체에 대해 메서드를 호출하면 최종적으로 이 메서드가 호출된다.
          String interfaceName = clazz.getName();
          String methodName = method.getName();
          String sqlId = interfaceName + "." + methodName;
          
          Class<?> retType = method.getReturnType();
          
          try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // select/insert/update/delete을 구분하여 메서드를 호출
            // 어떻게? 리턴 타입을 보고 결정한다.
            // => List라면 selectList()를 호출하고,
            // => 그냥 일반 객체라면 selectOne()을 호출하고,
            // => int 라면 insert()/update()/delete()을 호출한다.
            
            if (retType == List.class) {
              // 리턴 타입이 List 일 경우 select 문을 실행하는 것이다. 
              return (args == null) ? sqlSession.selectList(sqlId) :
                sqlSession.selectList(sqlId, args[0]);
            } else if (retType == int.class || retType == void.class) {
              // 리턴 타입이 int나 void 일 경우 insert/update/delete 문을 실행하는 것이다.
              // SqlSession 에서는 insert()/update()/delete() 중 아무거나 호출해도 똑 같다.
              return (args == null) ? sqlSession.insert(sqlId) :
                sqlSession.insert(sqlId, args[0]);
            } else {
              return (args == null) ? sqlSession.selectOne(sqlId) :
                sqlSession.selectOne(sqlId, args[0]);
            }
          }
        });
  }
}







