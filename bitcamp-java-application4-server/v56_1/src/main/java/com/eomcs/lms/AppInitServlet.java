package com.eomcs.lms;

import java.lang.reflect.Constructor;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.eomcs.util.RequestMappingHandlerMapping;

// 역할:
// => 다른 서블릿이 사용할 객체를 준비한다.
// => Spring IoC 컨테이너를 준비하여 ServletContext 보관소에 저장한다.
// => 이 서블릿은 클라이언트가 직접 실행하는 서블릿이 아니기 때문에 
//    서버에 배치할 때 URL을 지정하지 않는다.
//    또한 service() 메서드를 오버라이딩 하지 않는다.
//
public class AppInitServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  // Log4j의 로그 출력 도구를 준비한다.
  private static final Logger logger = LogManager.getLogger(AppInitServlet.class);

  ApplicationContext appCtx;
  RequestMappingHandlerMapping handlerMapping;
  int state;

  @Override
  public void init() throws ServletException {
    // Spring IoC 컨테이너의 클래스 이름을 가져오기
    String contextClass = getInitParameter("contextClass");
    if (contextClass != null) {
      try {
        // web.xml 에 설정한 IoC 컨테이너의 클래스를 로딩한다.
        Class<?> clazz = Class.forName(contextClass);
        
        // 해당 클래스의 생성자 중에서 자바 패키지 이름을 문자열로 받는 생성자를 꺼낸다.
        Constructor<?> constuctor = clazz.getConstructor(String[].class);
        
        // web.xml에서 IoC 컨테이너가 사용할 패키지 이름을 가져온다.
        String basePackageName = getInitParameter("contextConfigLocation");
        
        // 생성자를 이용하여 IoC 컨테이너 객체를 만든다.
        appCtx = (ApplicationContext) constuctor.newInstance(
            (Object) new String[] {basePackageName});
        
      } catch (Exception e) {
        throw new ServletException(e);
      }
    } else {
      // 파라미터로 전달한 패키지를 뒤져서
      // @Configuration 애노테이션이 붙은 클래스를 찾는다.
      // 이 애노테이션이 붙은 클래스가 Java Config 클래스이다.
      // IoC 컨테이너는 이 Java Config에 설정된 대로 객체를 준비한다.
      //
      appCtx = new AnnotationConfigApplicationContext("com.eomcs.lms.config");
    }
    
    // Spring IoC 컨테이너를 서블릿에서 사용할 수 있도록 ServletContext 보관소에 저장한다.
    getServletContext().setAttribute("iocContainer", appCtx);
    
    String[] beanNames = appCtx.getBeanDefinitionNames();
    logger.debug("[Spring IoC 컨테이너 객체들]------------");
    for (String beanName : beanNames) {
      logger.debug(String.format("%s(%s)",
          appCtx.getBean(beanName).getClass().getSimpleName(),
          beanName));
    }
    logger.debug("------------------------------------");
  }
}










