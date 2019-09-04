// 프로퍼티 값 변환기 - 프로퍼티 에디터를 사용하여 String을 java.util.Date 객체로 바꾸기
package ch29.g;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test03 {
  public static void main(String[] args) {
    ApplicationContext iocContainer = 
        new ClassPathXmlApplicationContext("ch29/g/application-context-03.xml");
    
    System.out.println("---------------------------------------");
    
    System.out.println(iocContainer.getBean("c1"));
  }
  
}






