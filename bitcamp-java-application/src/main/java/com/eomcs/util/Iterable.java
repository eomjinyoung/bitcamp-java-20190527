package com.eomcs.util;

// Iterator를 제공하는 클래스를 갖워야할 메서드 규칙을 정의한다.
// Iterator가 필요한 개발자는 다음 규칙에 따라 메서드를 호출하여 
// Iterator를 얻는다.
// 즉 Iterator를 얻는 방법도 일관성 있게 하기 위함이다.
// 이 방법을 도입하기 전에는 
// Stack 클래스에서는 getInterator() 메서드를 제공하였고,
// Queue 클래스에서는 createIterator() 메서드를 제공하였다.
// 이렇게 Iterator를 리턴해주는 메서드 이름이 다르면 
// 소스 코드를 유지보수하기가 불편하다.
// 
public interface Iterable<E> {
  
  // 다음 메서드를 호출하면 Iterator 구현체를 리턴할 것이다.
  // Stack과 Queue는 이 규칙을 준수해야 한다.
  // 그래야 개발자가 일관된 방식으로 Iterator를 얻을 수 있다.
  Iterator<E> iterator();
}











