package bitcamp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.util.UrlPathHelper;

@ComponentScan("bitcamp.app2")
@EnableWebMvc
public class App2Config implements WebMvcConfigurer {
  
  // DispatcherServlet의 기본 ViewResolver를 교체하기
  // 1) XML 설정
//  <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
//      <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
//      <property name="prefix" value="/WEB-INF/jsp/"/>
//      <property name="suffix" value=".jsp"/>
//  </bean>
//
  // 2) Java Config 설정
  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver vr = new InternalResourceViewResolver(
        "/WEB-INF/jsp2/", ".jsp");
    return vr;
  }
  
  // @MatrixVariable 애노테이션을 처리를 활성화시킨다.
  // => 이 작업을 수행하려면 MVC 관련 설정 작업을 수행할 수 있도록 
  //    WebMvcConfigurer 인터페이스를 구현해야 한다.
  // => 그리고 다음 메서드를 오버라이딩 하여 기존 설정을 변경한다.
  // 
  // DispatcherServlet 이 MVC 관련 설정을 처리하는 과정
  // => WebMVC 설정을 활성화시켰는지 검사한다.
  // => 활성화시켰으면, IoC 컨테이너의 Java Config 클래스 중에서 
  //    WebMvcConfigurer 구현체를 찾는다.
  // => WebMvcConfigurer 규칙에 따라 메서드를 호출하여 
  //    설정을 추가하거나 기존 설정을 변경한다.
  // => WebMVC 설정을 활성화시키지 않으면,
  //    WebMvcConfigurer 구현체가 있다 하더라도 무시한다.
  // => WebMVC 설정을 활성화시키는 방법
  //    1) XML 설정
  //         <mvc:annotation-driven/>
  //    2) Java Config 설정
  //         @EnableWebMvc 애노테이션 표시
  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    UrlPathHelper helper = new UrlPathHelper();
    helper.setRemoveSemicolonContent(false);
    
    // DispatcherServlet의 MVC Path 관련 설정을 변경한다.
    configurer.setUrlPathHelper(helper);
  }
}





