package com.eomcs.util;

import static org.reflections.ReflectionUtils.getMethods;
import static org.reflections.ReflectionUtils.withAnnotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// 역할:
// => @RequestMapping 애노테이션이 붙은 메서드의 정보를 보관한다.
// => 나중에 메서드를 호출하려면 인스턴스 주소도 알아야 하기 때문에
//    메서드 정보를 보관할 때 인스턴스도 함께 보관해야 한다.
public class RequestMappingHandlerMapping {
  // HashMap<명령어, (메서드 + 인스턴스)>
  HashMap<String,RequestHandler> handlerMap = new HashMap<>();
  
  @SuppressWarnings("unchecked")
  public RequestMappingHandlerMapping(ApplicationContext iocContainer) {
    // 페이지 컨트롤러를 꺼낸다. 즉 @Controller 애노테이션으로 표시된 객체를 꺼낸다.
    Collection<Object> objects = iocContainer.getBeansWithAnnotation(
        Controller.class).values();
    
    // 각 객체에 대해 request handler를 꺼낸다. 즉 @RequestMapping이 붙은 메서드를 꺼낸다.
    for (Object obj : objects) {
      Set<Method> methods = getMethods(
          obj.getClass(), 
          withAnnotation(RequestMapping.class));
      for (Method m : methods) {
        RequestMapping mapping = m.getAnnotation(RequestMapping.class);
        addRequestHandler(mapping.value()[0], obj, m);
      }
    }
  }
  
  private void addRequestHandler(String name, Object bean, Method method) {
    handlerMap.put(name, new RequestHandler(method, bean));
  }
  
  public RequestHandler getRequestHandler(String name) {
    return handlerMap.get(name);
  }
  
  public static class RequestHandler {
    public Method method;
    public Object bean;
    
    public RequestHandler(Method method, Object bean) {
      this.method = method;
      this.bean = bean;
    }
    
    public Object invoke(HttpServletRequest request, HttpServletResponse response) 
        throws Exception {
      return method.invoke(bean, request, response);
    }
  }
}






