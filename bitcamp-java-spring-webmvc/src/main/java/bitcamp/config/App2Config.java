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
  
  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
      configurer
          .setUrlPathHelper(urlPathHelper());
  }
  
  private UrlPathHelper urlPathHelper() {
    UrlPathHelper helper = new UrlPathHelper();
    helper.setRemoveSemicolonContent(false);
    return helper;
  }
}





