# bitcamp-java-web-library
bitcamp-java-spring-webmvc 프로젝트에서 사용할 라이브러리(.jar)를 생성한다.

## src.01

- ServletContainerInitializer 구현체를 만든다.
- /META-INF/services/java.servlet.ServletContainerIntializer 파일 생성
- 해당 파일에 구현체 클래스의 이름을 등록한다.
- 'gradle jar'를 실행하여 .jar 파일을 생성한다.
- .jar 파일을 웹 애플리케이션 프로젝트에 임포트 한 다음에 서블릿 컨테이너를 실행하라.
- 콘솔 창에 onStartup() 메서드가 호출된 것을 확인할 수 있을 것이다.
 