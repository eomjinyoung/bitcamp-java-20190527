# Spring Web MVC 예제

## src.01 : 웹 프로젝트 준비 

- HelloServlet 생성
- 서블릿 컨테이너에 배치 및 실행 테스트

## src.02 : 스프링 WebMVC 적용

- Spring Web MVC 라이브러리 추가
- HelloController 생성
- /WEB-INF/app-context.xml 파일 생성
  - Spring IoC 컨테이너 설정 파일
- /WEB-INF/web.xml 파일 준비
  - 프론트 컨트롤러 역할을 수행할 스프링 webmvc 에서 제공하는 서블릿을 배치한다.

## src.03 : IoC 설정 파일의 위치 

- /config/app-context.xml 로 위치 이동
- /WEB-INF/web.xml 변경

## src.04 : IoC 설정 파일의 위치 

- /WEB-INF/app-servlet.xml 로 위치 이동 및 이름 변경
- /WEB-INF/web.xml 변경
  - contextConfigLocation 초기화 파라미터 삭제

## src.05 : ContextLoaderListener와 DispatcherServlet의 IoC 컨테이너

- /WEB-INF/config/app-context.xml 로 위치 이동 및 이름 변경
  - <mvc:annotation-driven/> 태그 추가 
  - ContextLoaderListener는 WebMVC 관련 애노테이션을 처리할 객체가 없기 때문에
    <mvc:annotation-driven/> 태그를 사용하여 별도로 등록해야 한다.
- /WEB-INF/web.xml 변경
  - ContextLoaderListener 추가
  - ContextLoaderListener가 사용할 contextConfigLocation 파라미터 설정
  - DispatcherServlet에 contextConfigLocation 초기화 파라미터 추가. 값은 빈채로 된다.

## src.06 : ContextLoaderListener와 DispatcherServlet의 관계

- ContextLoaderListener의 IoC 컨테이너
  - 모든 프론트 컨트롤러 및 페이지 컨트롤러가 공유할 객체를 보관한다.
- DispatcherServlet의 IoC 컨테이너
  - 페이지 컨트롤러, 인터셉터 등 웹 관련 객체를 보관한다.
- /WEB-INF/config/app-context.xml 변경
- /WEB-INF/app-servlet.xml 변경
- /WEB-INF/admin-servlet.xml 추가
- /WEB-INF/web.xml 변경

## src.07 : Java Config로 DispatcherServlet의 IoC 컨테이너 설정하기

- bitcamp.AppConfig 클래스 생성
- /WEB-INF/web.xml 변경

## src.08 : SerlvetContainerInitializer 구현체의 활용 

- Spring WebMVC의 WebApplicationInitializer를 이해하기 위한 기반 기술 소개.
- bitcamp-java-web-library 프로젝트 준비
- 자세한 것은 해당 프로젝트의 README.md 파일을 읽어 볼 것.

## src.09 : web.xml 대신 WebApplicationInitializer 구현체에서 DispatcherServlet 등록하기

- WebApplicationInitializerImpl 생성
- web.xml 변경
  - DispatcherServlet 배치 정보 삭제

## src03 : Spring Web MVC 설정하기 - Java config 설정

- DispatcherServlet 이 사용할 IoC 컨테이너를 설정한다.
    - 자바 클래스로 설정하는 방법
    - WebApplicationInitializer 구현체를 이용하여 설정하는 방법

## src04 : Request Handler 정의하는 방법

- @Controller를 사용하여 페이지 컨트롤러 표시하기
- Request Handler의 아규먼트
- Request Handler의 리턴 타입과 View Resolver
- Request Handler의 URL @PathVariable 과  @MatrixVariable
- Request Handler의 @SessionAttributes와 @ModelAttribute
- 인터셉터 다루기