package com.eomcs.lms.config;

import javax.servlet.Filter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppWebApplicationInitializer 
  extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] {
      AppConfig.class, DatabaseConfig.class, MybatisConfig.class};
  }
  
  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] {WebConfig.class};
  }
  
  @Override
  protected String[] getServletMappings() {
    return new String[] {"/app/*"};
  }
  
  @Override
  protected String getServletName() {
    return "app";
  }
  
  @Override
  protected Filter[] getServletFilters() {
    return new Filter[] {
        new CharacterEncodingFilter("UTF-8")
        /*, new AuthFilter()*/ };
  }
}






