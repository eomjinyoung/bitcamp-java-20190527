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


## src02 : Spring Web MVC 설정하기 - XML 설정

- /WEB-INF/web.xml에 DispatcherServlet 클래스 적용
    - 이 클래스는 프론트 컨트롤러 역할을 수행한다.
- DispatcherServlet 이 사용할 IoC 컨테이너를 설정한다.
    - XML 설정 파일을 지정하는 방법

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