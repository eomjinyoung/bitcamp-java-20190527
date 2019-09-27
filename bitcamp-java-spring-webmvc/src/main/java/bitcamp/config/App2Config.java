package bitcamp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ComponentScan("bitcamp.app2")
public class App2Config {
  
  // DispatcherServlet의 기본 ViewResolver를 교체하기
  // 1) XML 설정
//  <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
//      <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
//      <property name="prefix" value="/WEB-INF/jsp/"/>
//      <property name="suffix" value=".jsp"/>
//  </bean>
//
  // 2) Java Config 설정
  //@Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver vr = new InternalResourceViewResolver(
        "/WEB-INF/jsp2/", ".jsp");
    return vr;
  }
}





