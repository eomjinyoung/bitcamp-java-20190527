package bitcamp;

import java.util.Set;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

// 서블릿 컨테이너가 시작될 때 보고를 받고 싶으면,
// => SerlvetContainerIntializer 규칙에 따라 작성해야 한다.
// => 이 클래스의 전체 이름(패키지명을 포함한 클래스명)을 다음 경로의 파일에 등록해야 한다.
//       /META-INF/services/javax.servlet.ServletContainerInitializer 파일
// 

@HandlesTypes(MyWebInitializer.class)
public class ServletContainerInitializerImpl 
  implements ServletContainerInitializer{

  @Override
  public void onStartup(Set<Class<?>> types, ServletContext ctx) throws ServletException {
    System.out.println("ServletContainerInitializerImpl.onStartup()...호출됨!");
    if (types == null)
      return;
    
    // 이 라이브러리 외부에 구현된 MyWebInitializer 구현체를 찾아 객체를 생성한다.
    for (Class<?> clazz : types) {
      try {
        System.out.println(clazz.getName() + ".start() 호출함!");
        MyWebInitializer obj = 
            (MyWebInitializer) clazz.getConstructor().newInstance();
        obj.start(ctx);
        
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}






