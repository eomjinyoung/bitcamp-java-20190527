// v59_2 : WebApplicationIntializer를 사용하여 DispatcherServlet 배치하기
package com.eomcs.lms;

// 작업:
// => com.eomcs.lms.controller 를 com.eomcs.lms.web 으로 패키지명 변경
// => com.eomcs.lms.config.WebConfig 생성
// => com.eomcs.lms.config.AppWebApplicationInitializer 생성
// => /WEB-INF/web.xml 변경
//    - DispatcherServlet 배치 정보 제거
//    - ContextLoaderListener 배치 정보 제거
//    - ContextLoaderListener 가 사용하는 컨텍스트 파라미터 정보 제거
// => AppConfig, DatabaseConfig, MybatisConfig 에서 @Configuration 제거
//    - AppWebApplicationInitializer 에서 이 Java Config 클래스를 직접 지정하기 때문이다. 
// => com.eomcs.lms.config.AppConfig 변경
//    - DispacherServlet의 IoC 컨테이너가 관리하는 com.eomcs.lms.web 패키지는 제외한다.
// => 기존의 JSP 폴더를 /WEB-INF/jsp 로 옮긴다.
// => 페이지 컨트롤러가 리턴하는 JSP URL을 InternalResourceViewResolver에 맞춰 변경한다.
//
// dummy 클래스!
// => 기존 버전에서 계속 존재했던 클래스라서 그대로 둠.
// => 단지 버전의 목표에 대한 설명을 기록하기 위해 존재함.
// => 프로젝트에서 사용되지 않음!
//
public class App {
}










