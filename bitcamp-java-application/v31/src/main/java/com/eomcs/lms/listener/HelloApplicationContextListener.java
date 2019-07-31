package com.eomcs.lms.listener;

import java.util.Map;
import com.eomcs.lms.context.ApplicationContextListener;

public class HelloApplicationContextListener implements ApplicationContextListener {
  long startMillis;
  
  @Override
  public void contextInitialized(Map<String,Object> beanContainer) {
    startMillis = System.currentTimeMillis();
    System.out.println("수업관리시스템에 오신을 환영합니다.");
  }
  
  @Override
  public void contextDestroyed(Map<String,Object> beanContainer) {
    System.out.println("수업관리시스템을 종료합니다. 안녕히 가세요!");
    int sec = (int)(System.currentTimeMillis() - startMillis) / 1000;
    System.out.printf("사용시간: %d초\n", sec);
  }
}







