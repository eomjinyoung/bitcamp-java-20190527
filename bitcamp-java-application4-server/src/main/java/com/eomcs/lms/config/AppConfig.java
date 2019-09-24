package com.eomcs.lms.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// Spring IoC 컨테이너에게 알려줄 설정 정보를 애노테이션을 이용하여 
// 이 클래스에 저장해 둔다.
// 
@Configuration

// com.eomcs.lms 패키지에서 @Component가 붙은 클래스를 찾아 인스턴스를 자동으로 생성하게 한다.
@ComponentScan("com.eomcs.lms")
@EnableWebMvc // Spring WebMVC 관련 애노테이션을 처리할 객체를 추가한다.
public class AppConfig {
}








