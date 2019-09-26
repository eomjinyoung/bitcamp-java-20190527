package bitcamp.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class App1WebApplicationInitializer 
  extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return null;
  }
  
  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] {App1Config.class};
  }
  
  @Override
  protected String[] getServletMappings() {
    return new String[] {"/app1/*"};
  }
  
  @Override
  protected String getServletName() {
    return "app1";
  }
  
  @Override
  protected void customizeRegistration(Dynamic registration) {
    // DispatcherServlet 이 multipart/form-data 으로 전송된 데이터를 처리하려면 
    // 해당 콤포넌트를 등록해야 한다.
    registration.setMultipartConfig(new MultipartConfigElement(
        "/tmp", // 업로드 한 파일을 임시 보관할 위치
        10000000, // 최대 업로드할 수 있는 파일들의 총 크기
        20000000, // 요청 전체 데이터의 크기
        2000000 // 업로드 되고 있는 파일을 메모리에 임시 임시 보관하는 크기
        ));
  }
}






